/**
 * 
 */
package com.aplus.portal.customer.bo;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ganesh
 *
 */
public class UploadedFile {
	
	public MultipartFile multipartFile;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
