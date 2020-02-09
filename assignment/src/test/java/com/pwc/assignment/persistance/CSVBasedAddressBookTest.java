package com.pwc.assignment.persistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.pwc.assignment.exception.AddressBookException;
import com.pwc.assignment.model.Contact;

public class CSVBasedAddressBookTest {

	
	private  AddressBook addressBookDao;

	@Before
	public void setup() throws IOException {
		File csvFile=new File(CSVBasedAddressBook.ADDRESS_BOOK_CSV_FILE_LOCATION);
		csvFile.delete();
		addressBookDao = new CSVBasedAddressBook();
	}
	
	@Test
	public void test_addMultipleContactAndReadWithNameOrder() throws AddressBookException {
		Contact person1 = new Contact("James", Stream.of("0410117456")
			      .collect(Collectors.toSet()));
		Contact person2 = new Contact("Anna", Stream.of("0410117555","0466117555")
			      .collect(Collectors.toSet()));
		addressBookDao.addToAddressBook(person1);
		addressBookDao.addToAddressBook(person2);
		Set<Contact> contacts=addressBookDao.getAllContacts();
		Assert.assertTrue(contacts.size()==2);
		List<Contact> testContacts=new ArrayList<Contact>();
		testContacts.add(person1);
		testContacts.add(person2);
		
		Assert.assertEquals(new HashSet<Contact>(testContacts),contacts);
	}
}
