package com.kiki.target.module.store;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kiki.target.common.model.Store;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.io.IORuntimeException;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年2月1日
 */
@Service
public class StoreService {
	
	@Value("${store.upload.path}")
	private  String STORE_PATH;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	StoreDao storeDao;

	/**
	 * Title: Description:
	 * 
	 * @param file
	 * @return
	 * @author jjtEatJava
	 * @throws IOException 
	 * @throws IORuntimeException 
	 * @date 2018年2月4日
	 */
	public Store store(MultipartFile file) throws IORuntimeException, IOException {
		String fileName = file.getOriginalFilename();// 获取完整名字
		String fileUploadName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		// 往文件名里面加上时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		int random = (int) (Math.random() * 1000000);
		String timeStamp = sdf.format(new Date());
		fileUploadName = timeStamp + random + fileUploadName.substring(fileUploadName.lastIndexOf(".")).toLowerCase();
		File targetFile = this.createFileByResourceName(fileUploadName);
		FileUtil.writeBytes(file.getBytes(), targetFile);//存储
		Store store = new Store();
		store.setCreateTime(new Date());
		store.setEnabled(true);
		store.setPath(targetFile.getAbsolutePath());
		store.setUpdateTime(new Date());
		this.save(store);
		return store;
	}

	/**
	 * Title:
	 * Description:
	 * @param fileUploadName
	 * @return
	 * @author jjtEatJava
	 * @throws IOException 
	 * @date 2018年2月4日
	 */
	private File createFileByResourceName(String fileUploadName) throws IOException {
		File parentFile = FileUtil.file(STORE_PATH);
		if(!parentFile.exists())
			if(!parentFile.mkdirs())
				throw new IllegalArgumentException("无法创建存储文件夹，存储失败");
		File targetFile = FileUtil.file(parentFile, fileUploadName);
		if(!targetFile.createNewFile())
			throw new IllegalArgumentException("无法创建存储文件，存储失败");
		return targetFile;
	}

	/**
	 * Title:
	 * Description:
	 * @param store
	 * @author jjtEatJava
	 * @date 2018年2月4日
	 */
	public void save(Store store) {
		storeDao.save(store);
	}

	/**
	 * Title:
	 * Description:
	 * @param storeId
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月4日
	 */
	public Resource loadAsResource(String storeId) {
		Store store = this.findByIdAndEnabled(storeId);
		if(store==null)
			throw new IllegalArgumentException("该id不存在");
		return resourceLoader.getResource("file:"+store.getPath());
	}

	/**
	 * Title:
	 * Description:
	 * @param storeId
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月4日
	 */
	public Store findByIdAndEnabled(String storeId) {
		return storeDao.findByIdAndEnabled(storeId,true);
	}

}
