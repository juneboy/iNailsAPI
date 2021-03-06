package mobile.api.iNails.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mobile.api.iNails.domain.Invoice;
import mobile.api.iNails.domain.Order;
import mobile.api.iNails.rowmapper.InvoiceRowMapper;
import mobile.api.iNails.rowmapper.OrderRowMapper;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Order> findAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOrder(int orderID) {
		String sql = "SELECT * FROM orders WHERE OrderID = :orderID";
		RowMapper<Order> rm = new OrderRowMapper();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("orderID", orderID);
		try {
			Order order = jdbcTemplate.queryForObject(sql, paramMap, rm);
			return order;
		} catch (DataAccessException e) {
			System.out.println("No order found cause "+ e);
			return null;
		}
		
	}

	@Override
	public int createOrder(Order order) {
		String sql = "INSERT INTO orders(CustomerID_FK,StatusID_FK,Date,TimeID_FK) VALUES (:customerID,:statusID,:orderDate,:timeID)";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("customerID", order.getCustomer().getCustomerID());
		paramMap.addValue("statusID", order.getStatus().getStatusID());
		paramMap.addValue("orderDate", order.getOrderDate());
		paramMap.addValue("timeID", order.getTimeReservation().getTimeID());
		
		try {
			int row = jdbcTemplate.update(sql, paramMap);
			return row;
		} catch (DataAccessException e) {
			System.out.println("Cannot insert new order cause "+e);
			return 0;
		}
	}

	@Override
	public int updateOrder(Order order) {
		String sql="ALTER TABLE Orders ";
		return 0;
	}

	@Override
	public int deleteOrder(int orderID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Invoice findInvoiceByOrderNumber(int orderID) {
		String sql = "SELECT o.OrderID,o.Date, GROUP_CONCAT(s.Name) as Services,sum(s.Time) as Duration,sum(s.price) as TotalPayment,c.Name as Customer,c.Phone as CustomerPhone,st.Name as Status, t.Time as TimeReservation, t.Date as DateReservation, a.Name as Artist"
				+" FROM Artist as a, Time as t,orders as o"
				+" INNER JOIN Customer as c ON c.CustomerID = o.CustomerID_FK"
				+" INNER JOIN Cart as ca ON ca.OrderID_FK = o.OrderID"
				+" INNER JOIN Status as st ON st.StatusID = o.StatusID_FK"
				+" JOIN Service as s WHERE s.ServiceID = ca.ServiceID_FK AND a.ArtistID= t.ArtistID_FK AND t.TimeID = o.TimeID_FK AND o.OrderID=:orderID";

		RowMapper<Invoice> rm = new InvoiceRowMapper();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("orderID", orderID);
		try {
			Invoice invoice = jdbcTemplate.queryForObject(sql,paramMap,rm);
			return invoice;
		} catch (DataAccessException e) {
			System.out.println("No invoice found cause "+ e);
			return null;
		}
	}

	@Override
	public int getLastInsert() {
		String sql = "SELECT * FROM orders ORDER BY orderID DESC LIMIT 1";
		RowMapper<Order> rm = new OrderRowMapper();
		return jdbcTemplate.query(sql, rm).get(0).getOrderID();
	}
}
