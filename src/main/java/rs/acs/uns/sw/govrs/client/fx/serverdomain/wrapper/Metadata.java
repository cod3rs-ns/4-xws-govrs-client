package rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Metadata {

    private Integer votesFor;

    private Integer votesAgainst;

    private Integer votesNeutral;

    private Date dateOfProposal;

    private Date dateOfVoting;

    private String status;

    public Metadata() {
        super();
    }

    public Metadata(Integer votesFor, Integer votesAgainst, Integer votesNeutral, Date dateOfProposal, Date dateOfVoting, String status) {
        this.votesFor = votesFor;
        this.votesAgainst = votesAgainst;
        this.votesNeutral = votesNeutral;
        this.dateOfProposal = dateOfProposal;
        this.dateOfVoting = dateOfVoting;
        this.status = status;
    }

    public Integer getVotesFor() {
        return votesFor;
    }

    public void setVotesFor(Integer votesFor) {
        this.votesFor = votesFor;
    }

    public Integer getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(Integer votesAgainst) {
        this.votesAgainst = votesAgainst;
    }

    public Integer getVotesNeutral() {
        return votesNeutral;
    }

    public void setVotesNeutral(Integer votesNeutral) {
        this.votesNeutral = votesNeutral;
    }

    public Date getDateOfProposal() {
        return dateOfProposal;
    }

    public void setDateOfProposal(Date dateOfProposal) {
        this.dateOfProposal = dateOfProposal;
    }

    public Date getDateOfVoting() {
        return dateOfVoting;
    }

    public void setDateOfVoting(Date dateOfVoting) {
        this.dateOfVoting = dateOfVoting;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
