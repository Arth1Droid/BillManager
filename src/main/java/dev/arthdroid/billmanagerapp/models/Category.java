package dev.arthdroid.billmanagerapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends AuditableEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "category")
	private List<Bill> bills =  new ArrayList<>();
	
	@Column(name = "name", nullable = false)
	private String name;
	
	public Category() {
		
	}
	

	public Category( String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("name cannot be null or blank.");
		}
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getId() {
		return id;
	}


	public List<Bill> getBills() {
		return bills;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
