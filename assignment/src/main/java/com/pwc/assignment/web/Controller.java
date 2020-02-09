package com.pwc.assignment.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pwc.assignment.service.AddressBookService;
import com.pwc.assignment.dto.ContactDTO;

@RestController
@RequestMapping(value = "/addressbook")
public class Controller {

	@Autowired
	private AddressBookService addressBookService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<ContactDTO>> getAllFriends() {

		return ResponseEntity.status(HttpStatus.OK).body(addressBookService.getAllContacts());
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public Object addFriend(@RequestBody ContactDTO conatctDto) {

		String contactName = addressBookService.addContact(conatctDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contactName);
	}
	
	@RequestMapping(value = "/uniquecontacts", method = RequestMethod.POST)
	public ResponseEntity<List<ContactDTO>> uniqueContacts(@RequestBody List<ContactDTO> addressBook) {

		List<ContactDTO>  uniqueContacts= addressBookService.findUniqueContacts(addressBook);
		
		return ResponseEntity.status(HttpStatus.OK).body(uniqueContacts);
	}

}
