package com.antonybaasan.receiptkeeper.restdata.serviceimpl;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import com.antonybaasan.receiptkeeper.restdata.model.UserProfile;
import com.antonybaasan.receiptkeeper.restdata.repositories.RecordRepository;
import com.antonybaasan.receiptkeeper.restdata.security.AuthFacade;
import com.antonybaasan.receiptkeeper.restdata.service.RecordService;
import com.antonybaasan.receiptkeeper.restdata.service.UserProfileService;
import com.antonybaasan.receiptkeeper.restdata.utils.RecordSpecification;
import com.antonybaasan.receiptkeeper.restdata.utils.SearchCriteria;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository repository;
    private AuthFacade auth;
    private UserProfileService userProsProfileService;

    private String notFoundText = "Can't find record with id %d";

    public RecordServiceImpl(RecordRepository repository, AuthFacade auth, UserProfileService userProsProfileService) {
        this.repository = repository;
        this.auth = auth;
        this.userProsProfileService = userProsProfileService;
    }

    public Page<Record> getRecords(String filterText, Date startDate, Date endDate, Pageable pageable) {

        String currentUserId = auth.getUser().getUid();
        Specification<Record> spec = Specification.where(new RecordSpecification(new SearchCriteria("owner", "=", currentUserId)));
        spec = this.updateSpec(spec, filterText, startDate, endDate);

        Page<Record> all = this.repository.findAll(spec, pageable);
        return all;
    }

    @Override
    public Record create(Record record) {
        String ownerId = auth.getUser().getUid();
        UserProfile user = userProsProfileService.getUserById(ownerId);
        // TODO: because of this for now Admin can't create something for someone
        record.setOwner(user);

        return this.repository.save(record);
    }

    @Override
    public Record update(Record record) throws IllegalAccessException, NotFoundException {
        Optional<Record> oldReceipt = this.repository.findById(record.getId());
        if (!oldReceipt.isPresent()) {
            throw new NotFoundException(String.format(notFoundText, record.getId()));
        }

        String currentUserId = auth.getUser().getUid();
        if (!oldReceipt.get().getOwner().equals(currentUserId)) {
            throw new IllegalAccessException();
        }

        return this.repository.save(record);
    }

    @Override
    public Record getRecord(long id) throws IllegalAccessException {

        Record record = this.repository.findById(id).get();

        String currentUserId = auth.getUser().getUid();
        if (!record.getOwner().equals(currentUserId)) {
            throw new IllegalAccessException();
        }

        return record;
    }

    @Override
    public int deleteRecords(List<Long> ids) throws IllegalAccessException {

        List<Record> receipts = this.repository.findByIdIn(ids);

        String currentUserId = auth.getUser().getUid();
        Optional<Record> invalidReceipt = receipts
                .stream()
                .filter(r -> !r.getOwner().equals(currentUserId))
                .findAny();

        if (invalidReceipt.isPresent()) {
            throw new IllegalAccessException();
        }

        return this.repository.deleteAllByIdIn(ids);
    }

    @Override
    public Record deleteRecord(Long id) throws NotFoundException, IllegalAccessException {
        Optional<Record> oldRecordOptional = this.repository.findById(id);
        if (!oldRecordOptional.isPresent()) {
            throw new NotFoundException(String.format(notFoundText, id));
        }
        Record oldRecord = oldRecordOptional.get();
        String currentUserId = auth.getUser().getUid();
        if (!oldRecord.equals(currentUserId)) {
            throw new IllegalAccessException();
        }
        this.repository.delete(oldRecord);
        return oldRecord;
    }

    private Specification<Record> updateSpec(Specification<Record> spec, String text, Date startDate, Date endDate) {
        if (text != null) {
            Specification<Record> specOr = Specification.where(new RecordSpecification(new SearchCriteria("title", ":", text)))
                    .or(new RecordSpecification(new SearchCriteria("description", ":", text)));
            spec = spec.and(specOr);
        }
        if (startDate != null) {
            spec = spec.and(new RecordSpecification(new SearchCriteria("date", ">", startDate)));
        }
        if (endDate != null) {
            spec = spec.and(new RecordSpecification(new SearchCriteria("date", "<", endDate)));
        }

        return spec;
    }
}
