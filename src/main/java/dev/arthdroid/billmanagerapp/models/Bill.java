package dev.arthdroid.billmanagerapp.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill")
public class Bill extends AuditableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "pay_date")
	private LocalDate payDay;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "bill_status", nullable = false)
	private BillStatus status;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "cost", nullable = false, precision = 19, scale = 2)
	private BigDecimal cost;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Bill() {
	}

	public Bill(Category category, BigDecimal cost, String description, LocalDate dueDate) {
		if (dueDate == null) {
			throw new IllegalArgumentException("Due date cannot be null.");
		}
		if (category == null) {
			throw new IllegalArgumentException("category cannot be null.");
		}
		setCost(cost);
		setDescription(description);

		this.dueDate = dueDate;
		this.category = category;
		this.payDay = null;
		this.status = BillStatus.OPEN;
	}

	public void payBill(LocalDate paymentDateTime) {
		if (!(status == BillStatus.OPEN || status == BillStatus.OVERDUE)) {
			throw new IllegalArgumentException("The bill is already paid");
		}
		if (paymentDateTime == null) {
			throw new IllegalArgumentException("The payment date/time cannot be null.");
		}

		this.payDay = paymentDateTime;
		this.status = BillStatus.PAID;
	}

	public void verifyDueDate(LocalDate today) {
		if (today == null) {
			throw new IllegalArgumentException("The baseline date cannot be null.");
		}
		if (today.isAfter(dueDate) && status == BillStatus.OPEN) {
			status = BillStatus.OVERDUE;
		}
	}

	public Long remainingDays(LocalDate today) {
		if (today == null) {
			throw new IllegalArgumentException("The baseline date cannot be null.");
		}
		if (status == BillStatus.OVERDUE) {
			return 0L;
		}

		long daysBetweenDates = ChronoUnit.DAYS.between(today, dueDate);
		return Math.max(0L, daysBetweenDates);
	}

	public boolean isOverdue() {
		return status == BillStatus.OVERDUE;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("The description cannot be blank or null.");
		}
		this.description = description;
	}

	public LocalDate getPayDay() {
		return payDay;
	}

	public void setPayDay(LocalDate payDay) {
		this.payDay = payDay;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		if (dueDate == null) {
			throw new IllegalArgumentException("The due date cannot be null");
		}
		this.dueDate = dueDate;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("Status cannot be null");
		}
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Category cannot be null");
		}
		this.category = category;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		if (cost == null || cost.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("The cost cannot be zero, negative or null");
		}
		this.cost = cost;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
