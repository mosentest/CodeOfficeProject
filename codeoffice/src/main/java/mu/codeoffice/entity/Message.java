package mu.codeoffice.entity;

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

@Entity
@Table(name = "office_message")
public class Message implements Serializable {

	private static final long serialVersionUID = -1127763294462749873L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")	
	private EnterpriseUser sender;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver_id")	
	private EnterpriseUser receiver;

	@Column(name = "content")
	private String content;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;

	@Column(name = "replied")
	private boolean replied;

	@Column(name = "_read")
	private boolean read;

	@Column(name = "sender_removed")
	private boolean senderRemoved;

	@Column(name = "receiver_removed")
	private boolean receiverRemoved;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Message parent;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private List<Message> conversation;
	
	public Message() {}

}
