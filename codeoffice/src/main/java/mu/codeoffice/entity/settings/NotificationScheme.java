package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.User;

@Entity
@Table(name = "notificationscheme", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "enterprise_id"}))
public class NotificationScheme implements Serializable {

	private static final long serialVersionUID = -5377717154325394662L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "name")
	@Size(max = 30)
	private String name;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "creator_id")
	private User creator;

	@Column(name = "description")
	@Size(max = 200)
	private String description;
	
	@Column(name = "modified_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date modified;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notificationScheme")
	private List<Notification> notifications;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notificationScheme")
	private List<Project> projects;
	
	public NotificationScheme() {}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NotificationScheme)) {
			return false;
		}
		NotificationScheme o = (NotificationScheme) obj;
		return o.id != null && this.id != null && o.id.equals(this.id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
