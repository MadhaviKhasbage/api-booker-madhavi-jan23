package pojo.request.createbooking;

public class CreateBookingRequest {
	public String firstname;
	public String lastname;
	public int totalprice;
	public boolean depositpaid;
	public Bookingdates bookingdates;
	public String additionalneeds;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public boolean isDepositpaid() {
		return depositpaid;
	}

	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}

	public Bookingdates getBookingdates() {
		return bookingdates;
	}

	public void setBookingdates(Bookingdates bookingdates) {
		this.bookingdates = bookingdates;
	}

	public String getAdditionalneeds() {
		return additionalneeds;
	}

	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}

	@Override
	public int hashCode() {
		return firstname.length(); 
	}

	@Override
	public boolean equals(Object obj) {		
		CreateBookingRequest other = (CreateBookingRequest) obj;
		if (additionalneeds == null) {
			if (other.additionalneeds != null)
				return false;
		} else if (!additionalneeds.equals(other.additionalneeds))
			return false;
		if (bookingdates == null) {
			if (other.bookingdates != null)
				return false;
		} else if (!bookingdates.equals(other.bookingdates))
			return false;
		if (depositpaid != other.depositpaid)
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (totalprice != other.totalprice)
			return false;
		return true;
	}

	

}