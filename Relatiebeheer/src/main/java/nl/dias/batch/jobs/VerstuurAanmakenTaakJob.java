//package nl.dias.batch.jobs;
//
//import nl.dias.messaging.sender.AanmakenTaakSender;
//import nl.lakedigital.as.messaging.AanmakenTaak;
//import nl.lakedigital.as.messaging.AanmakenTaak.SoortTaak;
//import org.quartz.*;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//@PersistJobDataAfterExecution
//public class VerstuurAanmakenTaakJob implements Job {
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AanmakenTaakSender sender = (AanmakenTaakSender) applicationContext.getBean("aanmakenTaakSender");
//        AanmakenTaak aanmakenTaak = new AanmakenTaak();
//        aanmakenTaak.setSoort(SoortTaak.INVULLEN_BELASTINGPAPIEREN);
//
//        sender.send(aanmakenTaak);
//        JobDataMap data = context.getJobDetail().getJobDataMap();
//        data.put("Aangemaakt : ", aanmakenTaak.getSoort());
//    }
//}
