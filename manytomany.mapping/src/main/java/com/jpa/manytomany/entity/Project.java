package com.jpa.manytomany.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@JsonProperty("Project_uuid")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid = UUID.randomUUID().toString();
//	@JsonProperty("Project_name")
	private String projectName;
//	@JsonProperty("duration_month")
	private int DurationMonth;
	private LocalDateTime projectJoingDate;
	private LocalDateTime modicationDate;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getDurationMonth() {
		return DurationMonth;
	}
	public void setDurationMonth(int durationMonth) {
		DurationMonth = durationMonth;
	}
	public void setProjectJoingDate(LocalDateTime projectJoingDate) {
		this.projectJoingDate = projectJoingDate;
	}

	public void setModicationDate(LocalDateTime modicationDate) {
		this.modicationDate = modicationDate;
	}
	
	@Override
	public String toString() {
		return "Project [id=" + id + ", uuid=" + uuid + ", projectName=" + projectName + ", DurationMonth="
				+ DurationMonth + ", projectJoingDate=" + projectJoingDate + ", modicationDate=" + modicationDate + "]";
	}
	
}
