package com.pwc.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.pwc.assignment.exception.AddressBookException;
import com.pwc.assignment.model.Contact;
import com.pwc.assignment.persistance.AddressBook;
import com.pwc.assignment.dto.ContactDTO;


public class AddressBookServiceTest {

	@Mock
	private AddressBook addressBook;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AddressBookService addressBookService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_getAllContacts() throws AddressBookException {
		Stream<String> jacksNumbers = Stream.of("0410227456");
		Contact contact1 = new Contact("Jack", jacksNumbers.collect(Collectors.toSet()));
		Stream<String> jillsNumbers = Stream.of("0410117444", "0555117555");
		Contact contact2 = new Contact("Jil", jillsNumbers.collect(Collectors.toSet()));
		
		when(addressBook.getAllContacts()).thenReturn(new HashSet<Contact>(Arrays.asList(contact1, contact2)));
		
		ContactDTO jackDto = new ContactDTO("Jack", jacksNumbers.collect(Collectors.toList()));
		when(modelMapper.map(contact1, ContactDTO.class))
				.thenReturn(jackDto);
		
		ContactDTO jillDto = new ContactDTO("Jil", jillsNumbers.collect(Collectors.toList()));
		when(modelMapper.map(contact1, ContactDTO.class))
		.thenReturn(jillDto);
		
		List<ContactDTO> actual=addressBookService.getAllContacts();
		
		List<ContactDTO> expected=new ArrayList<ContactDTO>();
		expected.add(jackDto);
		expected.add(jillDto);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_AddContact() throws AddressBookException {
		Stream<String> jemmaNumbers = Stream.of("0410858456");
		Contact contact = new Contact("Jemma", jemmaNumbers.collect(Collectors.toSet()));
		
		when(addressBook.addToAddressBook(contact)).thenReturn("Jemma");
		
		ContactDTO jemmaDto = new ContactDTO("Jemma", jemmaNumbers.collect(Collectors.toList()));
		when(modelMapper.map(contact, ContactDTO.class))
				.thenReturn(jemmaDto);
		
		String actual=addressBookService.addContact(jemmaDto);
	
		assertEquals("Jemma", actual);
	}
	
	@Test
	public void test_FindUniqueContacts() throws AddressBookException {
		
		
		ContactDTO bobDto = new ContactDTO("Bob", Stream.of("0410227456").collect(Collectors.toList()));
		ContactDTO maryDto = new ContactDTO("Mary", Stream.of("0410227444").collect(Collectors.toList()));
		ContactDTO janeDto = new ContactDTO("Jane", Stream.of("041066ß7444").collect(Collectors.toList()));
		List<ContactDTO> savedContacts=new ArrayList<ContactDTO>();
		savedContacts.add(bobDto);
		savedContacts.add(maryDto);
		savedContacts.add(janeDto);
		when(addressBookService.getAllContacts()).thenReturn(savedContacts);
		
		ContactDTO maryNewDto = new ContactDTO("Mary", Stream.of("0410227444").collect(Collectors.toList()));
		ContactDTO johnDto = new ContactDTO("John", Stream.of("0555227456").collect(Collectors.toList()));
		ContactDTO janeNewDto = new ContactDTO("Jane", Stream.of("041066ß7444").collect(Collectors.toList()));
		List<ContactDTO> newContacts=new ArrayList<ContactDTO>();
		newContacts.add(maryNewDto);
		newContacts.add(johnDto);
		newContacts.add(janeNewDto);
		
		
		
		
		List<ContactDTO> uniqueContactsExpected=new ArrayList<ContactDTO>();
		uniqueContactsExpected.add(bobDto);
		uniqueContactsExpected.add(johnDto);
				
		List<ContactDTO> uniqueContactsActual =addressBookService.findUniqueContacts(newContacts);
		assertEquals(uniqueContactsExpected, uniqueContactsActual);
	}

}
