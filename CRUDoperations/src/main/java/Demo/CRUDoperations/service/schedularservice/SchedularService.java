package Demo.CRUDoperations.service.schedularservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedularService {




    @Scheduled(cron = "${cronExpression}")
    public void reportSchedular(){

    }
}
