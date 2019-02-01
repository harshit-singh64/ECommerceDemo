package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.jwt.JwtTokenValidator;
import com.example.demo.pdfReport.PdfReportService;
import com.example.demo.service.IUserService;
import com.itextpdf.text.DocumentException;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api")
public class PdfReportController {
	@Autowired
	private IUserService userService;
	@Autowired
	private JwtTokenValidator jwtTokenValidator;
	
	private Jedis jedis = new Jedis("127.0.0.1", 6379);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InputStreamResource> generatePdf(@PathVariable(value = "id") @Valid Integer userId, 
			HttpServletRequest httpServletRequest) throws CustomException, FileNotFoundException, DocumentException {
		
		try {
			String token = httpServletRequest.getHeader("Authorization");
			System.out.println("token " + token);
			String username = jedis.get(token);
			System.out.println("username " + username);
			//String username = jedis.get(token);
			
			if(username != null) {
				List<UserDto> userDtoList = new ArrayList<>();
				//userDto = jwtTokenDecoder.tokenDecoder(token);
				userDtoList = jwtTokenValidator.tokenValidator(token);
				
				System.out.println("validated and decoded token : " + userDtoList);
				
				ArrayList roleNameList = (ArrayList) userDtoList.get(0).getRoleDto();
				LinkedHashMap<Object, Object> roleNameMap = (LinkedHashMap<Object, Object>) roleNameList.get(0);
				//System.out.println(roleNameMap.get("name")+"==============name");
				UserDto userDtoFromDatabase = new UserDto();
				List<UserDto> userDtoListFromDatabase = new ArrayList<>();
				userDtoListFromDatabase = userService.displayAllUsers();
				
				System.out.println("dto from datbase " + userDtoListFromDatabase);
				
				System.out.println(roleNameMap.get("name").equals("User"));
				System.out.println(userDtoFromDatabase.getEmail());
				System.out.println(username);
				
				//System.out.println(userDtoFromDatabase.getId().equals(userDto.getId()));
				
				if(roleNameMap.get("name").equals("Admin")) {
					//HttpHeaders responseHeader = new HttpHeaders();
					ByteArrayInputStream bis = PdfReportService.generatePdfReportForAdmin(userDtoList, userDtoListFromDatabase);
					
					HttpHeaders headers = new HttpHeaders();
			        headers.add("Content-Disposition", "inline; filename=pdf_report.pdf");
			        
					//PdfReport.generatePdfReport();
					//return new  ResponseEntity<>(HttpStatus.OK);
			        return ResponseEntity
			                .ok()
			                .headers(headers)
			                .contentType(MediaType.APPLICATION_PDF)
			                .body(new InputStreamResource(bis));
					}
				else if(roleNameMap.get("name").equals("User")) /*&&
						(username.equals(userDtoFromDatabase.getEmail()) &&
						userDtoFromDatabase.getId().equals(userDtoFromDatabase.getId())))*/ {
					
					ByteArrayInputStream bis = PdfReportService.generatePdfReportForUser(userDtoList);
					
					HttpHeaders headers = new HttpHeaders();
			        headers.add("Content-Disposition", "inline; filename=pdf_report.pdf");
			        
					//PdfReport.generatePdfReport();
					//return new  ResponseEntity<>(HttpStatus.OK);
			        return ResponseEntity
			                .ok()
			                .headers(headers)
			                .contentType(MediaType.APPLICATION_PDF)
			                .body(new InputStreamResource(bis));
				}
				else {
					throw new CustomException(400,"Invalid Access","you are not allowed in this area");
					}
				}
			else {
				System.out.println("else block");
				throw new CustomException(400,"Invalid Input","Invalid token input");
				}
			}
		catch (CustomException e) {
			//e.printStackTrace();
			throw e;
			}
		catch (Exception e) {
			//e.printStackTrace();
			throw e;
			}
			
		/*ByteArrayInputStream bis = PdfReportService.generatePdfReport();
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=pdf_report.pdf");
        
		//PdfReport.generatePdfReport();
		//return new  ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));*/
		}
	
	/*@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> generatePdf() throws FileNotFoundException, DocumentException {
		ByteArrayInputStream bis = PdfReportService.generatePdfReport();
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=pdf_report.pdf");
        
		//PdfReport.generatePdfReport();
		//return new  ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
		}*/
	
	}
