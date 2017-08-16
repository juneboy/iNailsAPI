package mobile.api.iNails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mobile.api.iNails.dao.OrderDao;
import mobile.api.iNails.domain.Invoice;

@RestController
@RequestMapping(value="/rest/api/")
public class OrderController {
	@Autowired
	private OrderDao orderDao;
	
	@RequestMapping(value="invoices/{orderID}",method = RequestMethod.GET)
	public ResponseEntity<Invoice> findInvoiceByOrderNumber(@PathVariable("orderID") Integer orderID){
		Invoice invoice = orderDao.findInvoiceByOrderNumber(orderID);
		return (invoice == null)? new ResponseEntity<Invoice>(HttpStatus.NO_CONTENT) : new ResponseEntity<Invoice>(invoice,HttpStatus.OK);
	}

}