package com.arangodb.springframework.repository.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isOneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.springframework.core.convert.DBDocumentEntity;
import com.arangodb.springframework.repository.AbstractArangoRepositoryTest;
import com.arangodb.springframework.testdata.Customer;
import com.arangodb.springframework.testdata.CustomerNameProjection;

/**
 *
 * @author Audrius Malele
 * @author Mark McCormick
 * @author Mark Vollmary
 * @author Christian Lechner
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ArangoAqlQueryTest extends AbstractArangoRepositoryTest {

	private final AqlQueryOptions OPTIONS = new AqlQueryOptions();

	@Test
	public void findOneByIdAqlWithNamedParameterTest() {
		repository.save(customers);
		final Map<String, Object> retrieved = repository.findOneByIdAqlWithNamedParameter(john.getId(), OPTIONS);
		final Customer retrievedCustomer = template.getConverter().read(Customer.class, new DBDocumentEntity(retrieved));
		assertEquals(john, retrievedCustomer);
	}

	@Test
	public void findOneByIdAndNameAqlTest() {
		repository.save(customers);
		final BaseDocument retrieved = repository.findOneByIdAndNameAql(john.getId(), john.getName());
		final Map<String, Object> allProperties = new HashMap<>();
		allProperties.put("_id", retrieved.getId());
		allProperties.put("_key", retrieved.getKey());
		allProperties.put("_rev", retrieved.getRevision());
		retrieved.getProperties().forEach((k, v) -> allProperties.put(k, v));
		final Customer retrievedCustomer = template.getConverter().read(Customer.class,
				new DBDocumentEntity(allProperties));
		assertEquals(john, retrievedCustomer);
	}

	@Test
	public void findOneByBindVarsAqlTest() {
		repository.save(customers);
		final Map<String, Object> bindVars = new HashMap<>();
		bindVars.put("id", john.getId());
		bindVars.put("name", john.getName());
		final ArangoCursor<Customer> retrieved = repository.findOneByBindVarsAql(OPTIONS.ttl(127).cache(true), bindVars);
		assertEquals(john, retrieved.next());
	}

	@Test
	public void findOneByComplementingNameAndBindVarsAqlTest() {
		repository.save(customers);
		final Map<String, Object> bindVars = new HashMap<>();
		bindVars.put("id", john.getId());
		final Customer retrieved = repository.findOneByNameAndBindVarsAql(john.getName(), bindVars);
		assertEquals(john, retrieved);
	}

	@Test
	public void findOneByOverridingNameAndBindVarsAqlTest() {
		repository.save(customers);
		final Map<String, Object> bindVars = new HashMap<>();
		bindVars.put("id", john.getId());
		bindVars.put("name", john.getId() + "random");
		final Customer retrieved = repository.findOneByNameAndBindVarsAql(john.getName(), bindVars);
		assertEquals(john, retrieved);
	}

	@Test
	public void findOneByIdAndNameWithBindVarsAqlTest() {
		repository.save(customers);
		final Map<String, Object> bindVars = new HashMap<>();
		bindVars.put("id", john.getId());
		bindVars.put("0", john.getName() + "random");
		final Customer retrieved = repository.findOneByIdAndNameWithBindVarsAql(john.getName(), bindVars);
		assertEquals(john, retrieved);
	}

	@Test(expected = ArangoDBException.class)
	public void findOneByIdInCollectionAqlWithUnusedParamTest() {
		repository.save(customers);
		final Customer retrieved = repository.findOneByIdInCollectionAqlWithUnusedParam(john.getId().split("/")[0],
				john.getId(), john.getId());
		assertEquals(john, retrieved);
	}

	@Test(expected = ArangoDBException.class)
	public void findOneByIdInNamedCollectionAqlWithUnusedParamTest() {
		repository.save(customers);
		final Customer retrieved = repository.findOneByIdInNamedCollectionAqlWithUnusedParam(john.getId().split("/")[0],
				john.getId(), john.getId());
		assertEquals(john, retrieved);
	}

	@Test(expected = ArangoDBException.class)
	public void findOneByIdInIncorrectNamedCollectionAqlTest() {
		repository.save(customers);
		final Customer retrieved = repository.findOneByIdInIncorrectNamedCollectionAql(john.getId().split("/")[0],
				john.getId(), john.getId());
		assertEquals(john, retrieved);
	}

	@Test(expected = ArangoDBException.class)
	public void findOneByIdInNamedCollectionAqlRejectedTest() {
		repository.save(customers);
		final Customer retrieved = repository.findOneByIdInNamedCollectionAqlRejected(john.getId().split("/")[0],
				john.getId());
		assertEquals(john, retrieved);
	}

	@Test
	public void findManyBySurnameTest() {
		final List<Customer> toBeRetrieved = new LinkedList<>();
		toBeRetrieved.add(new Customer("James", "Smith", 35));
		toBeRetrieved.add(new Customer("Matt", "Smith", 34));
		repository.save(toBeRetrieved);
		final List<Customer> retrieved = repository.findManyBySurname("Smith");
		assertTrue(equals(retrieved, toBeRetrieved, cmp, eq, false));

	}

	@Test
	public void queryCount() {
		assertEquals(repository.queryCount(Customer.class), 0L);
	}

	@Test
	public void queryDate() {
		assertEquals(repository.queryDate(), Instant.ofEpochMilli(1474988621));
	}

	@Test
	public void findOneByIdNamedQueryTest() {
		repository.save(customers);
		final Customer retrieved = repository.findOneByIdNamedQuery(john.getId());
		assertEquals(john, retrieved);
	}

	@Test
	public void findOneByIdWithStaticProjectionTest() {
		repository.save(customers);
		final CustomerNameProjection retrieved = repository.findOneByIdWithStaticProjection(john.getId());
		assertEquals(retrieved.getName(), john.getName());
	}

	@Test
	public void findManyLegalAgeWithStaticProjectionTest() {
		repository.save(customers);
		final List<CustomerNameProjection> retrieved = repository.findManyLegalAgeWithStaticProjection();
		for (CustomerNameProjection proj : retrieved) {
			assertThat(proj.getName(), isOneOf(john.getName(), bob.getName()));
		}
	}

	@Test
	public void findOneByIdWithDynamicProjectionTest() {
		repository.save(customers);
		final CustomerNameProjection retrieved = repository.findOneByIdWithDynamicProjection(john.getId(),
				CustomerNameProjection.class);
		assertEquals(retrieved.getName(), john.getName());
	}

	@Test
	public void findManyLegalAgeWithDynamicProjectionTest() {
		repository.save(customers);
		final List<CustomerNameProjection> retrieved = repository
				.findManyLegalAgeWithDynamicProjection(CustomerNameProjection.class);
		for (CustomerNameProjection proj : retrieved) {
			assertThat(proj.getName(), isOneOf(john.getName(), bob.getName()));
		}
	}

}
