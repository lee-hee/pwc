package com.pwc.assignment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pwc.assignment.exception.AddressBookException;
import com.pwc.assignment.model.Contact;
import com.pwc.assignment.persistance.AddressBook;
import com.pwc.assignment.dto.ContactDTO;

@Component
public class AddressBookService {
	
	private Logger logger = LoggerFactory.getLogger(AddressBookService.class);

	@Autowired
	private AddressBook addressBook;

	@Autowired
	private ModelMapper modelMapper;

	
	public List<ContactDTO> getAllContacts() {

		try {
			Set<Contact> contacts = addressBook.getAllContacts();

			List<ContactDTO> dtos = contacts.stream().
					map(cotact -> mapToDto(cotact)).collect(Collectors.toList());
			logger.info("Found {} contacts",dtos.size());
			 Collections.sort(dtos);
			 return dtos;

		} catch (AddressBookException e) {
			logger.error("Failed to read Friends : {} ", e.getMessage());
			return Collections.emptyList();
		}
	}
	
	public String addContact(ContactDTO conatctDto) {
		try {
			String addedContactName=addressBook.addToAddressBook(mapToEntity(conatctDto));
			logger.info("Contact created for {} ",conatctDto.getName());
			return addedContactName;
		} catch (AddressBookException e) {
			logger.error("Failed add contact : {} ", e.getMessage());
			return null;
		}
		
	}

	public List<ContactDTO> findUniqueContacts(List<ContactDTO> addressBookDTO) {
		
		List<ContactDTO> contactsUniqueToEachAddressBook=new ArrayList<ContactDTO>();
		
		List<ContactDTO> currentContacts=getAllContacts();
		
		List<ContactDTO> newContacts=addressBookDTO;
		
		for(ContactDTO dto:currentContacts)
		{
			if(!newContacts.contains(dto))
			{
				contactsUniqueToEachAddressBook.add(dto);
			}
		}
		
		for(ContactDTO dto:newContacts)
		{
			if(!currentContacts.contains(dto))
			{
				contactsUniqueToEachAddressBook.add(dto);
			}
		}
		
		return contactsUniqueToEachAddressBook;
	}
	
	private ContactDTO mapToDto(Contact cotact) {
		return modelMapper.map(cotact, ContactDTO.class);
	}
	
	private Contact mapToEntity(ContactDTO cotactDto) {
		return modelMapper.map(cotactDto, Contact.class);
	}

}
