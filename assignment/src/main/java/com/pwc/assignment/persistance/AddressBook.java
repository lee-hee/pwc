package com.pwc.assignment.persistance;

import java.util.Set;

import com.pwc.assignment.exception.AddressBookException;
import com.pwc.assignment.model.Contact;

public interface AddressBook {

	public String addToAddressBook(Contact contact) throws AddressBookException;
	
	public Set<Contact> getAllContacts() throws AddressBookException;
	
}
