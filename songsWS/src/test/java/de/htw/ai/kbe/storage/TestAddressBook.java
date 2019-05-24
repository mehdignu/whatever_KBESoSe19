package de.htw.ai.kbe.storage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.htw.ai.kbe.bean.Address;
import de.htw.ai.kbe.bean.Contact;

public class TestAddressBook {

	private static Map<Integer,Contact> storage;
	
	public TestAddressBook() {
		storage = new ConcurrentHashMap<Integer,Contact>();
		init();
	}
	
	private static void init() {
		Address testAddress = new Address.Builder()
				.street("Teststr.")
				.city("Test")
				.zip("12345")
				.country("Germany").build();
		
		Contact testerContact = new Contact.Builder(1,"Detlef")
				.lastName("Tester")
				.mobile("+4917612345678")
				.emailAddress("as@gmx.de")
				.address(testAddress).build();
		
		storage.put(testerContact.getId(), testerContact);
	}
	
	public Contact getContact(Integer id) {
		return storage.get(id);
	}
	
	public Collection<Contact> getAllContacts() {
		return storage.values();
	}
	
	public Integer addContact(Contact contact) {
		contact.setId((int)storage.keySet().stream().count() + 1);
		storage.put(contact.getId(), contact);
		return contact.getId();
	}
	
	public boolean updateContact(Contact contact) {
		throw new UnsupportedOperationException("updateContact: not yet implemented");
	}
	
	public Contact deleteContact(Integer id) {
		throw new UnsupportedOperationException("deleteContact: not yet implemented");
	}
}
