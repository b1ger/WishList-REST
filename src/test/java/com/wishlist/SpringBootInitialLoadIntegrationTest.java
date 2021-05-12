package com.wishlist;

import com.wishlist.repository.AuthorityRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishlistApplication.class)
@Sql({"/sql/authority_data.sql", "/sql/drop_authorities.sql"})
public class SpringBootInitialLoadIntegrationTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    @Sql("/sql/authority_data.sql")
    @Sql(value = "/sql/drop_authorities.sql", executionPhase = AFTER_TEST_METHOD)
    public void testLoadDataForTestClass() {
        assertEquals(3, authorityRepository.findAllByOrderByName().size());
    }
}
