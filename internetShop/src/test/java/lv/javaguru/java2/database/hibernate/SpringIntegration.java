package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.servlet.mvc.spring.SpringAppConfig;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by Anton on 2015.03.15..
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringAppConfig.class)
@TransactionConfiguration(defaultRollback = false)
public class SpringIntegration {

}