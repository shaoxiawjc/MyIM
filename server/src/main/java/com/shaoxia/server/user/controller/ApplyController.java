package com.shaoxia.server.user.controller;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.common.utils.JwtUtils;
import com.shaoxia.server.user.model.vo.BaseResponse;
import com.shaoxia.server.user.model.vo.ResultUtils;
import com.shaoxia.server.user.model.vo.apply.GetApplyResp;
import com.shaoxia.server.user.model.vo.apply.SendApplyReq;
import com.shaoxia.server.user.model.vo.apply.SolveApplyReq;
import com.shaoxia.server.user.service.impl.ApplyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.shaoxia.server.common.exception.ErrorCode.*;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-13 16:19
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/apply")
public class ApplyController {
	@Resource
	private ApplyServiceImpl applyService;

	/**
	 * 发送好友请求
	 * @param sendApplyReq
	 * @param request
	 * @return
	 */
	@PutMapping("/send")
	public BaseResponse<Void> sendApply(SendApplyReq sendApplyReq, HttpServletRequest request){
		String targetUid = "";
		String msg = "";
		if (Objects.isNull(sendApplyReq)){
			throw new BusinessException(NULL_ERROR,"发送数据为空");
		}else {
			targetUid = sendApplyReq.getTargetUid();
			msg = sendApplyReq.getMsg();
			if (StringUtils.isAnyBlank(targetUid,msg)){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
		}
		String fromUid = JwtUtils.getUid(request);
		applyService.sendApply(targetUid,msg,fromUid);
		return ResultUtils.success(null);
	}

	/**
	 * 查看申请列表
	 * @param request
	 * @return
	 */
	@GetMapping("/get")
	public BaseResponse<List<GetApplyResp>> getApply(HttpServletRequest request){
		String fromUid = JwtUtils.getUid(request);
		List<GetApplyResp> resp = applyService.getApply(fromUid);
		return ResultUtils.success(resp);
	}

	/**
	 * 处理好友申请
	 * @param solveApplyReq
	 * @param request
	 * @return
	 */
	@PutMapping("/solve")
	public BaseResponse<Void> solveApply(SolveApplyReq solveApplyReq,HttpServletRequest request){
		Integer decide = -1;
		String applyId = "";
		if (Objects.isNull(solveApplyReq)){
			throw new BusinessException(NULL_ERROR,"发送数据为空");
		}else {
			decide = solveApplyReq.getDecide();
			applyId = solveApplyReq.getApplyId();
			if (StringUtils.isAnyBlank(applyId) || Objects.isNull(decide)){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
			if (!(decide == 1 || decide == 0)){
				throw new BusinessException(PARAMS_ERROR,"decide只能为0或1");
			}
		}
		String fromUid = JwtUtils.getUid(request);
		applyService.solveApply(decide,applyId,fromUid);
		return ResultUtils.success(null);
	}
}
