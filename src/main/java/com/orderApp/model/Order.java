package com.orderApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name="item")
	public String item;

	@Column(name="quantity")
	public int quantity;

	@Column(name="amount")
	public double amount;

	@Column(name="status")
	public String status;

}
