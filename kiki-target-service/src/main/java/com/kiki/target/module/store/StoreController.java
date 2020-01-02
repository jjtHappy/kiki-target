package com.kiki.target.module.store;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;


import org.dom4j.IllegalAddException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kiki.target.common.model.Store;
import com.kiki.target.common.response.ResultMapBuilder;
import com.xiaoleilu.hutool.http.HttpResponse;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.io.IORuntimeException;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年2月1日
 */
@Controller
@RequestMapping("/store")
public class StoreController {
	@Autowired
	StoreService storeService;
	@Value("${store.picture.suffix}")
	private String pictureSuffix = "jpg|png";
	private static Logger logger = LoggerFactory.getLogger(StoreController.class);

	@PostMapping("/upload/picture")
	@ResponseBody
	public Map<String, Object> uploadPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws IORuntimeException, IOException {
		validatePicture(file);
		Store store = storeService.store(file);
		Map<String, Object> storeMap = new HashMap<String, Object>();
		storeMap.put("storeId", store.getId());
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		return ResultMapBuilder.buildSuccessMap(storeMap, null);
	}

	/**
	 * Title: Description:
	 * 
	 * @param file
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	private void validatePicture(MultipartFile file) {
		if (file.getSize() == 0)
			throw new IllegalAddException("文件内容为空，请重新选择文件！");
		// 获取文件名称
		String fileName = file.getOriginalFilename();
		// 判断文件扩展名是否包含在指定字符串内
		String extName = FileUtil.extName(fileName).toLowerCase();
		if (!extName.matches(pictureSuffix))
			throw new IllegalArgumentException("只支持" + pictureSuffix + "格式");
	}

	@RequestMapping("/download/picture")
	@ResponseBody
	public Object downloadPicture(String storeId) throws IOException {
		logger.info(storeId);
		if (StrUtil.isBlank(storeId))
			throw new IllegalArgumentException("id不能为空");
		Resource file = storeService.loadAsResource(storeId);
		String contentType = null;
		if (FileUtil.extName(file.getFile()).equals("png")) {
			contentType="image/png";
		}
		if (FileUtil.extName(file.getFile()).equals("jpg")) {
			contentType="image/jpeg";
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, contentType).header(HttpHeaders.ACCEPT_RANGES, "bytes").body(file);
	}

}
