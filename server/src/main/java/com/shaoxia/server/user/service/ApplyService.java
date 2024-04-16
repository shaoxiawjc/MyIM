package com.shaoxia.server.user.service;

import com.shaoxia.server.user.model.vo.apply.GetApplyResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplyService {
	void sendApply(String targetUid, String msg,String fromUid);

	List<GetApplyResp> getApply(String fromUid);

	void solveApply(Integer decide, String applyId, String fromUid);
}
