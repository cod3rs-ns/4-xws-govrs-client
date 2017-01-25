package rs.acs.uns.sw.govrs.client.fx.serverdomain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "voting_object")
public class VotingObject {

    @XmlElement(name = "votes_for")
    private Integer votesFor;

    @XmlElement(name = "votes_against")
    private Integer votesAgainst;

    @XmlElement(name = "votes_neutral")
    private Integer votesNeutral;

    public VotingObject() {
    }

    public VotingObject(Integer votesFor, Integer votesAgainst, Integer votesNeutral) {
        this.votesFor = votesFor;
        this.votesAgainst = votesAgainst;
        this.votesNeutral = votesNeutral;
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

    @Override
    public String toString() {
        return "VotingObject{" +
                "votesFor=" + votesFor +
                ", votesAgainst=" + votesAgainst +
                ", votesNeutral=" + votesNeutral +
                '}';
    }
}