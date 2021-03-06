package mobile.api.iNails.domain;

import java.sql.Date;

public interface Order {
	public int getOrderID();

	public void setOrderID(int orderID);

	public CustomerImpl getCustomer();

	public void setCustomer(CustomerImpl customer);

	public StatusImpl getStatus();

	public void setStatus(StatusImpl status);

	public Date getOrderDate();

	public void setOrderDate(Date orderDate);

	public Time getTimeReservation();
	public void setTimeReservation(Time timeReservation);


}
