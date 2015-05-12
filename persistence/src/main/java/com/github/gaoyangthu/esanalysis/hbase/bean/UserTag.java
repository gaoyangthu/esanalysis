package com.github.gaoyangthu.esanalysis.hbase.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-14
 * Time: 上午9:38
 */
@Deprecated
public class UserTag implements Serializable {

	private String bdUserUuid;

	private List<ChannelTag> channelTags;

	private List<TradeTag> tradeTags;

	public UserTag() {
	}

	public UserTag(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public UserTag(String bdUserUuid, List<ChannelTag> channelTags,
			List<TradeTag> tradeTags) {
		this.bdUserUuid = bdUserUuid;
		this.channelTags = channelTags;
		this.tradeTags = tradeTags;
	}

	public String getBdUserUuid() {
		return bdUserUuid;
	}

	public void setBdUserUuid(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public List<ChannelTag> getChannelTags() {
		return channelTags;
	}

	public void setChannelTags(List<ChannelTag> channelTags) {
		this.channelTags = channelTags;
	}

	public List<TradeTag> getTradeTags() {
		return tradeTags;
	}

	public void setTradeTags(List<TradeTag> tradeTags) {
		this.tradeTags = tradeTags;
	}

	public class ChannelTag {
		private String channelId;
		private String channelCode;
		private String channelName;
		private String channelTime;

		public ChannelTag() {
		}

		public ChannelTag(String channelId, String channelCode,
				String channelName, String channelTime) {
			this.channelId = channelId;
			this.channelCode = channelCode;
			this.channelName = channelName;
			this.channelTime = channelTime;
		}

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

		public String getChannelCode() {
			return channelCode;
		}

		public void setChannelCode(String channelCode) {
			this.channelCode = channelCode;
		}

		public String getChannelName() {
			return channelName;
		}

		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}

		public String getChannelTime() {
			return channelTime;
		}

		public void setChannelTime(String channelTime) {
			this.channelTime = channelTime;
		}
	}

	public class TradeTag {
		private String tradeId;
		private String tradeName;
		private BigDecimal tradeAmount;
		private Date tradeTime;

		public TradeTag() {
		}

		public TradeTag(String tradeId, String tradeName,
				BigDecimal tradeAmount, Date tradeTime) {
			this.tradeId = tradeId;
			this.tradeName = tradeName;
			this.tradeAmount = tradeAmount;
			this.tradeTime = tradeTime;
		}

		public String getTradeId() {
			return tradeId;
		}

		public void setTradeId(String tradeId) {
			this.tradeId = tradeId;
		}

		public String getTradeName() {
			return tradeName;
		}

		public void setTradeName(String tradeName) {
			this.tradeName = tradeName;
		}

		public BigDecimal getTradeAmount() {
			return tradeAmount;
		}

		public void setTradeAmount(BigDecimal tradeAmount) {
			this.tradeAmount = tradeAmount;
		}

		public Date getTradeTime() {
			return tradeTime;
		}

		public void setTradeTime(Date tradeTime) {
			this.tradeTime = tradeTime;
		}
	}

}
