package co.tz.qroo.spa.system_log;

import co.tz.qroo.spa.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;

    public SystemLogService(final SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public List<SystemLogDTO> findAll() {
        final List<SystemLog> systemLogs = systemLogRepository.findAll(Sort.by("id"));
        return systemLogs.stream()
                .map(systemLog -> mapToDTO(systemLog, new SystemLogDTO()))
                .toList();
    }

    public SystemLogDTO get(final Long id) {
        return systemLogRepository.findById(id)
                .map(systemLog -> mapToDTO(systemLog, new SystemLogDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SystemLogDTO systemLogDTO) {
        final SystemLog systemLog = new SystemLog();
        mapToEntity(systemLogDTO, systemLog);
        return systemLogRepository.save(systemLog).getId();
    }

    public void update(final Long id, final SystemLogDTO systemLogDTO) {
        final SystemLog systemLog = systemLogRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(systemLogDTO, systemLog);
        systemLogRepository.save(systemLog);
    }

    public void delete(final Long id) {
        systemLogRepository.deleteById(id);
    }

    private SystemLogDTO mapToDTO(final SystemLog systemLog, final SystemLogDTO systemLogDTO) {
        systemLogDTO.setId(systemLog.getId());
        systemLogDTO.setActionedBy(systemLog.getActionedBy());
        systemLogDTO.setActivity(systemLog.getActivity());
        systemLogDTO.setType(systemLog.getType());
        return systemLogDTO;
    }

    private SystemLog mapToEntity(final SystemLogDTO systemLogDTO, final SystemLog systemLog) {
        systemLog.setActionedBy(systemLogDTO.getActionedBy());
        systemLog.setActivity(systemLogDTO.getActivity());
        systemLog.setType(systemLogDTO.getType());
        return systemLog;
    }

}
