package com.github.gaoyangthu.esanalysis.meta.service;

import com.github.gaoyangthu.esanalysis.meta.bean.UrlBehaviorRule;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-19
 * Time: 下午4:09
 */
public interface UrlBehaviorRuleService {

	UrlBehaviorRule findByUrlRegex(String urlRegex);

	UrlBehaviorRule findByUserBehavior(Integer userBehaviorId);

	List<UrlBehaviorRule> findAll();

}
