package com.pwc.assignment.persistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pwc.assignment.exception.AddressBookException;
import com.pwc.assignment.model.Contact;

@Component
@Scope("singleton")
public class CSVBasedAddressBook implements AddressBook {

	public static String ADDRESS_BOOK_CSV_FILE_LOCATION = "/opt/addressbook/addressbook.csv";

	public String addToAddressBook(Contact contact) throws AddressBookException {

		CSVPrinter csvPrinter = null;
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(ADDRESS_BOOK_CSV_FILE_LOCATION),
					StandardOpenOption.APPEND, StandardOpenOption.CREATE);

			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

			csvPrinter.printRecord(contact.getName(), String.join(",", contact.getPhoneNumbers()));
			return contact.getName();
		} catch (Exception e) {
			throw new AddressBookException("Error when writing to " + ADDRESS_BOOK_CSV_FILE_LOCATION);
		} finally {
			try {
				csvPrinter.close();
			} catch (IOException e) {
				throw new AddressBookException("Could not close opened file at " + ADDRESS_BOOK_CSV_FILE_LOCATION);
			}
		}
	}

	public Set<Contact> getAllContacts() throws AddressBookException {

		Set<Contact> contacts = new HashSet<Contact>();

		CSVParser csvParser = null;

		try {
			Reader reader = Files.newBufferedReader(Paths.get(ADDRESS_BOOK_CSV_FILE_LOCATION));

			csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

			for (CSVRecord csvRecord : csvParser) {

				String name = csvRecord.get(0);
				Set<String> phoneNumbers = new HashSet<String>();

				int i = 0;
				String phoneNumber = null;
				do {
					++i;
					try {
						phoneNumber = csvRecord.get(i);
					} catch (Exception e) {
						// Not a valid column
						break;
					}
					phoneNumbers.add(phoneNumber);

				} while (phoneNumber != null);

				Contact contact = new Contact(name, phoneNumbers);

				contacts.add(contact);

			}
		} catch (IOException e) {
			throw new AddressBookException("Could not read contacts from " + ADDRESS_BOOK_CSV_FILE_LOCATION);
		} finally {
			try {
				csvParser.close();
			} catch (IOException e) {
				throw new AddressBookException("Could not close opened file at " + ADDRESS_BOOK_CSV_FILE_LOCATION);
			}
		}

		return contacts;

	}

}
