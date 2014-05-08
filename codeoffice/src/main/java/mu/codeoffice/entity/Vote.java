package mu.codeoffice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "vote")
public class Vote implements Serializable {

	private static final long serialVersionUID = 6776148354584052841L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name = "unique_id")
	private String uniqueId;

    @Column(name = "title", nullable = false, unique = true)
	private String title;

	@ManyToOne(cascade = CascadeType.REMOVE, optional = true)
	@JoinColumn(name = "user_id")
	private User creator;

	@Column(name = "create_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date create;

	@Column(name = "expire_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date expire;

    @Column(name = "votes")
	private int votes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vote", fetch = FetchType.LAZY)
	private List<VoteOption> voteOptions;

    @Column(name = "enable_ip_restriction")
	private boolean enableIpRestriction;

    @Column(name = "enable_multiple_selection")
	private boolean enableMultipleSelection;

    @Column(name = "anonymous")
	private boolean anonymous;
	
	public Vote() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public List<VoteOption> getVoteOptions() {
		return voteOptions;
	}

	public void setVoteOptions(List<VoteOption> voteOptions) {
		this.voteOptions = voteOptions;
	}

	public boolean isEnableIpRestriction() {
		return enableIpRestriction;
	}

	public void setEnableIpRestriction(boolean enableIpRestriction) {
		this.enableIpRestriction = enableIpRestriction;
	}

	public boolean isEnableMultipleSelection() {
		return enableMultipleSelection;
	}

	public void setEnableMultipleSelection(boolean enableMultipleSelection) {
		this.enableMultipleSelection = enableMultipleSelection;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	
}
