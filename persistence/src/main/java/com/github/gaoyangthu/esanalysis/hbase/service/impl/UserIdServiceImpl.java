package com.github.gaoyangthu.esanalysis.hbase.service.impl;

import com.github.gaoyangthu.core.hbase.HbaseTemplate;
import com.github.gaoyangthu.core.hbase.HbaseTemplateFactory;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserId;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserIndex;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserIdConst;
import com.github.gaoyangthu.esanalysis.hbase.dao.UserIdMapper;
import com.github.gaoyangthu.esanalysis.hbase.dao.UserIndexMapper;
import com.github.gaoyangthu.esanalysis.hbase.service.UserBehaviorService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserChannelTagService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserStatService;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 上午9:53
 */
public class UserIdServiceImpl implements UserIdService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserIdServiceImpl.class);

	private HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

	private UserBehaviorService userBehaviorService;

	private UserChannelTagService userChannelTagService;

	private UserStatService userStatService;

	public UserIdServiceImpl() {
		userBehaviorService = new UserBehaviorServiceImpl();
		userChannelTagService = new UserChannelTagServiceImpl();
		userStatService = new UserStatServiceImpl();
	}

	@Override
	public List<UserId> findAllUserId() {
		return hbaseTemplate.find(UserIdConst.USER_ID, new Scan(), new UserIdMapper());
	}

	@Override
	public List<UserIndex> findAllUserIndex() {
		return hbaseTemplate.find(UserIdConst.USER_INDEX, new Scan(), new UserIndexMapper());
	}

	@Override
	public String getBdUserUuid(String uaId, String cookieId, String accountId) {
		UserIndex uaIndex = getUserIndexById(uaId);
		UserIndex cookieIndex = getUserIndexById(cookieId);
		UserIndex accountIndex = getUserIndexById(accountId);

		boolean flag = false;

		String bdUserUuid = null;
		if(accountIndex != null) {
			// 存在accountIndex
			// 更新其他的index的uuid到accountIndex
			bdUserUuid = accountIndex.getBdUserUuid();

			if (cookieIndex != null) {
				if (!cookieIndex.getBdUserUuid().equals(bdUserUuid)) {
					String oldUuid = cookieIndex.getBdUserUuid();
					// TODO 更新tag对应的uuid,将oldUuid的tag赋值给新的bdUserUuid,删除oldUuid.
					userBehaviorService.changeBdUser(oldUuid, bdUserUuid);
					userChannelTagService.changeBdUser(oldUuid, bdUserUuid);

					// 更新user_index
					flag = addUserIndex(cookieId, bdUserUuid);
					if(!flag) {
						LOGGER.error(
								"Update user_index error. cookieId={}, bdUserUuid={}",
								cookieId, bdUserUuid);
					}
				}
			} else if (StringUtils.isNotBlank(cookieId)) {
				// cookieIndex == null && cookieId != null
				flag = addUserIndex(cookieId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. cookieId={}, bdUserUuid={}",
							cookieId, bdUserUuid);
				}
			}

			if (uaIndex != null) {
				if (!uaIndex.getBdUserUuid().equals(bdUserUuid)) {
					String oldUuid = uaIndex.getBdUserUuid();
					// TODO 更新tag对应的uuid,将oldUuid的tag赋值给新的bdUserUuid,删除oldUuid.
					userBehaviorService.changeBdUser(oldUuid, bdUserUuid);
					userChannelTagService.changeBdUser(oldUuid, bdUserUuid);

					// 更新user_index
					flag = addUserIndex(uaId, bdUserUuid);
					if(!flag) {
						LOGGER.error(
								"Update user_index error. uaId={}, bdUserUuid={}",
								uaId, bdUserUuid);
					}
				}
			} else if (StringUtils.isNotBlank(uaId)) {
				// uaIndex == null && uaId != null
				flag = addUserIndex(uaId, bdUserUuid);
				if(!flag) {
					LOGGER.error("Create user_index error. uaId={}, bdUserUuid={}",
							uaId, bdUserUuid);
				}
			}
		} else if(cookieIndex != null) {
			bdUserUuid = cookieIndex.getBdUserUuid();

			if (StringUtils.isNotBlank(accountId)) {
				// accountIndex == null && accountId != null
				flag = addUserIndex(accountId, bdUserUuid);
				if (!flag) {
					LOGGER.error(
							"Create user_index error. accountId={}, bdUserUuid={}",
							accountId, bdUserUuid);
				}
			}

			if (uaIndex != null) {
				if (!uaIndex.getBdUserUuid().equals(bdUserUuid)) {
					String oldUuid = uaIndex.getBdUserUuid();
					// TODO 更新tag对应的uuid,将oldUuid的tag赋值给新的bdUserUuid,删除oldUuid.
					userBehaviorService.changeBdUser(oldUuid, bdUserUuid);
					userChannelTagService.changeBdUser(oldUuid, bdUserUuid);

					// 更新user_index
					flag = addUserIndex(uaId, bdUserUuid);
					if(!flag) {
						LOGGER.error(
								"Update user_index error. uaId={}, bdUserUuid={}",
								uaId, bdUserUuid);
					}
				}
			} else if (StringUtils.isNotBlank(uaId)) {
				// uaIndex == null && uaId != null
				flag = addUserIndex(uaId, bdUserUuid);
				if(!flag) {
					LOGGER.error("Create user_index error. uaId={}, bdUserUuid={}",
							uaId, bdUserUuid);
				}
			}

		} else if(uaIndex != null) {
			bdUserUuid = uaIndex.getBdUserUuid();

			if (StringUtils.isNotBlank(accountId)) {
				// accountIndex == null && accountId != null
				flag = addUserIndex(accountId, bdUserUuid);
				if (!flag) {
					LOGGER.error(
							"Create user_index error. accountId={}, bdUserUuid={}",
							accountId, bdUserUuid);
				}
			}

			if (StringUtils.isNotBlank(cookieId)) {
				flag = addUserIndex(cookieId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. cookieId={}, bdUserUuid={}",
							cookieId, bdUserUuid);
				}
			}

		} else {
			// bdUserUuid为空,表示没有生成过bdUserUuid
			bdUserUuid = createBdUserUuid();

			// 添加userIndex
			if (StringUtils.isNotBlank(uaId)) {
				flag = addUserIndex(uaId, bdUserUuid);
				if(!flag) {
					LOGGER.error("Create user_index error. uaId={}, bdUserUuid={}",
							uaId, bdUserUuid);
				}
			}
			if (StringUtils.isNotBlank(cookieId)) {
				flag = addUserIndex(cookieId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. cookieId={}, bdUserUuid={}",
							cookieId, bdUserUuid);
				}
			}
			if (StringUtils.isNotBlank(accountId)) {
				flag = addUserIndex(accountId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. accountId={}, bdUserUuid={}",
							accountId, bdUserUuid);
				}
			}
		}

		// 添加userId
		flag = addUserId(bdUserUuid, uaId, cookieId, accountId);
		if (!flag) {
			LOGGER.error(
					"Create user_id error. bdUserUuid={}, uaId={}, cookieId={}, accountId={}",
					bdUserUuid, uaId, cookieId, accountId);
		}

		return bdUserUuid;
	}

	public String getBdUuid(String uaId, String cookieId, String accountId) {
		// TODO 代码可以优化,用hashmap<id, UserIndex>做后续的遍历和访问
		UserIndex uaIndex = getUserIndexById(uaId);
		UserIndex cookieIndex = getUserIndexById(cookieId);
		UserIndex accountIndex = getUserIndexById(accountId);

		String bdUserUuid = null;
		Long timestamp = Long.MAX_VALUE;

		if ((uaIndex != null) && (uaIndex.getTimestamp() < timestamp)) {
			bdUserUuid = uaIndex.getBdUserUuid();
			timestamp = uaIndex.getTimestamp();
		}
		if ((cookieIndex != null) && (cookieIndex.getTimestamp() < timestamp)) {
			bdUserUuid = cookieIndex.getBdUserUuid();
			timestamp = cookieIndex.getTimestamp();
		}
		if ((accountIndex != null) && (accountIndex.getTimestamp() < timestamp)) {
			bdUserUuid = accountIndex.getBdUserUuid();
			timestamp = accountIndex.getTimestamp();
		}

		boolean flag = false;
		if (StringUtils.isBlank(bdUserUuid)) {
			// bdUserUuid为空,表示没有生成过bdUserUuid
			bdUserUuid = createBdUserUuid();
			// 添加userId
			flag = addUserId(bdUserUuid, uaId, cookieId, accountId);
			if (!flag) {
				LOGGER.error(
						"Create user_id error. bdUserUuid={}, uaId={}, cookieId={}, accountId={}",
						bdUserUuid, uaId, cookieId, accountId);
			}
			// 添加userIndex
			if (StringUtils.isNotBlank(uaId)) {
				flag = addUserIndex(uaId, bdUserUuid);
				if(!flag) {
					LOGGER.error("Create user_index error. uaId={}, bdUserUuid={}",
							uaId, bdUserUuid);
				}
			}
			if (StringUtils.isNotBlank(cookieId)) {
				flag = addUserIndex(cookieId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. cookieId={}, bdUserUuid={}",
							cookieId, bdUserUuid);
				}
			}
			if (StringUtils.isNotBlank(accountId)) {
				flag = addUserIndex(accountId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. accountId={}, bdUserUuid={}",
							accountId, bdUserUuid);
				}
			}
			return bdUserUuid;
		} else {
			// bdUserUuid != null, 存在一个创建时间最早的bdUserUuid
			// 更新不一致的id(ua_id,cookie_id,account_id对应的bdUserUuid)
			if (uaIndex != null) {
				if (!uaIndex.getBdUserUuid().equals(bdUserUuid)) {
					String oldUuid = uaIndex.getBdUserUuid();
					// TODO 更新tag对应的uuid,将oldUuid的tag赋值给新的bdUserUuid,删除oldUuid.
					userBehaviorService.changeBdUser(oldUuid, bdUserUuid);
					userChannelTagService.changeBdUser(oldUuid, bdUserUuid);
					userStatService.changeBdUser(oldUuid, bdUserUuid);

					// 更新user_index
					flag = addUserIndex(uaId, bdUserUuid);
					if(!flag) {
						LOGGER.error(
								"Update user_index error. uaId={}, bdUserUuid={}",
								uaId, bdUserUuid);
					}
				}
			} else if (StringUtils.isNotBlank(uaId)) {
				// uaIndex == null && uaId != null
				flag = addUserIndex(uaId, bdUserUuid);
				if(!flag) {
					LOGGER.error("Create user_index error. uaId={}, bdUserUuid={}",
							uaId, bdUserUuid);
				}
			}

			if (cookieIndex != null) {
				if (!cookieIndex.getBdUserUuid().equals(bdUserUuid)) {
					String oldUuid = cookieIndex.getBdUserUuid();
					// TODO 更新tag对应的uuid,将oldUuid的tag赋值给新的bdUserUuid,删除oldUuid.
					userBehaviorService.changeBdUser(oldUuid, bdUserUuid);
					userChannelTagService.changeBdUser(oldUuid, bdUserUuid);
					userStatService.changeBdUser(oldUuid, bdUserUuid);

					// 更新user_index
					flag = addUserIndex(cookieId, bdUserUuid);
					if(!flag) {
						LOGGER.error(
								"Update user_index error. cookieId={}, bdUserUuid={}",
								cookieId, bdUserUuid);
					}
				}
			} else if (StringUtils.isNotBlank(cookieId)) {
				// cookieIndex == null && cookieId != null
				flag = addUserIndex(cookieId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. cookieId={}, bdUserUuid={}",
							cookieId, bdUserUuid);
				}
			}

			if (accountIndex != null) {
				if (!accountIndex.getBdUserUuid().equals(bdUserUuid)) {
					String oldUuid = accountIndex.getBdUserUuid();
					// TODO 更新tag对应的uuid,将oldUuid的tag赋值给新的bdUserUuid,删除oldUuid.
					userBehaviorService.changeBdUser(oldUuid, bdUserUuid);
					userChannelTagService.changeBdUser(oldUuid, bdUserUuid);
					userStatService.changeBdUser(oldUuid, bdUserUuid);

					// 更新user_index
					flag = addUserIndex(accountId, bdUserUuid);
					if(!flag) {
						LOGGER.error(
								"Update user_index error. accountId={}, bdUserUuid={}",
								accountId, bdUserUuid);
					}
				}
			} else if (StringUtils.isNotBlank(accountId)) {
				// accountIndex == null && accountId != null
				flag = addUserIndex(accountId, bdUserUuid);
				if(!flag) {
					LOGGER.error(
							"Create user_index error. accountId={}, bdUserUuid={}",
							accountId, bdUserUuid);
				}
			}

			// 更新userId
			flag = addUserId(bdUserUuid, uaId, cookieId, accountId);
			if (!flag) {
				LOGGER.error(
						"Update user_id error. bdUserUuid={}, uaId={}, cookieId={}, accountId={}",
						bdUserUuid, uaId, cookieId, accountId);
			}
		}

		return bdUserUuid;
	}

	private String createBdUserUuid() {
		return UUID.randomUUID().toString();
	}

	public UserIndex getUserIndexById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		UserIndex userIndex = hbaseTemplate.get(UserIdConst.USER_INDEX, id,
				new UserIndexMapper());
		return userIndex;
	}

	public boolean addUserId(String bdUserUuid, String uaId, String cookieId,
			String accountId) {
		boolean flag = false;

		HTableInterface userIdTable = hbaseTemplate.getTable(UserIdConst.USER_ID);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			// id列族
			// id:trade_id
			if (StringUtils.isNotBlank(uaId)) {
				put.add(Bytes.toBytes(UserIdConst.ID),
						Bytes.toBytes(UserIdConst.UA_ID), Bytes.toBytes(uaId));
			}

			// id:cookie_id
			if (StringUtils.isNotBlank(cookieId)) {
				put.add(Bytes.toBytes(UserIdConst.ID),
						Bytes.toBytes(UserIdConst.COOKIE_ID),
						Bytes.toBytes(cookieId));
			}

			// id:account_id
			if (StringUtils.isNotBlank(accountId)) {
				put.add(Bytes.toBytes(UserIdConst.ID),
						Bytes.toBytes(UserIdConst.ACCOUNT_ID),
						Bytes.toBytes(accountId));
			}

			userIdTable.put(put);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("Add user id error", e);
		} finally {
			hbaseTemplate.releaseTable(UserIdConst.USER_ID, userIdTable);
		}

		return flag;
	}

	public boolean addUserIndex(String id, String bdUserUuid) {
		boolean flag = false;

		HTableInterface userIdTable = hbaseTemplate.getTable(UserIdConst.USER_INDEX);
		try {
			Put put = new Put(Bytes.toBytes(id));

			// bd_user_uuid列族
			// bd_user_uuid:bd_user_uuid
			put.add(Bytes.toBytes(UserIdConst.BD_USER_UUID),
					Bytes.toBytes(UserIdConst.BD_USER_UUID),
					Bytes.toBytes(bdUserUuid));

			userIdTable.put(put);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("Add user index error", e);
		} finally {
			hbaseTemplate.releaseTable(UserIdConst.USER_INDEX, userIdTable);
		}

		return flag;
	}
}
