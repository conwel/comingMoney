package com.cn.conwel.domain;

public class FundBean {
   private String netWorth;
   private String increase;
   private String interest;
   private String time;
   private String code;
   private String rich;

    /**
 * @return the code
 */
public String getCode() {
	return code;
}
/**
 * @param code the code to set
 */
public void setCode(String code) {
	this.code = code;
}
/**
 * @return the netWorth
 */
public String getNetWorth() {
	return netWorth;
}
/**
 * @param netWorth the netWorth to set
 */
public void setNetWorth(String netWorth) {
	this.netWorth = netWorth;
}
/**
 * @return the increase
 */
public String getIncrease() {
	return increase;
}
/**
 * @param increase the increase to set
 */
public void setIncrease(String increase) {
	this.increase = increase;
}
/**
 * @return the interest
 */
public String getInterest() {
	return interest;
}
/**
 * @param interest the interest to set
 */
public void setInterest(String interest) {
	this.interest = interest;
}
/**
 * @return the time
 */
public String getTime() {
	return time;
}
/**
 * @param time the time to set
 */
public void setTime(String time) {
	this.time = time;
}

public String getRich() {
    return rich;
}

public void setRich(String rich) {
    this.rich = rich;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "FundBean [netWorth=" + netWorth + ", increase=" + increase
			+ ", interest=" + interest + ", time=" + time + "]";
}

}
