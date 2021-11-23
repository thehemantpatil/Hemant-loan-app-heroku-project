package com.finzly.loanapp.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finzly.loanapp.dao.UserDao;
import com.finzly.loanapp.dto.LoanConstraintDto;
import com.finzly.loanapp.dto.LoanDto;
import com.finzly.loanapp.service.LoanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("loan")
@CrossOrigin(value = "*")
public class LoanController {

	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserDao userDao;

	@ApiOperation(value = "get loan constraint", response = LoanConstraintDto.class)
	@GetMapping("/constraint")
	public ResponseEntity<LoanConstraintDto> getLoanConstraints() {
		try {
			LoanConstraintDto loanConstraints = new LoanConstraintDto();
			return ResponseEntity.ok(loanConstraints);
		} catch (Exception e) {
			return ResponseEntity.status(204).build();
		}

	}

	@GetMapping("/")
	@ApiOperation(value = "get all loans", response = LoanDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 204, message = "No Content") })
	public ResponseEntity<LoanDto> fetchLoanDetails(@RequestParam Map<String, Long> customer) {
		try {
			if (customer != null) {
				List<Map> loanObject = loanService.fetchLoanDetails(customer);
				logger.info("Received list of loan having size: {}", loanObject.size());

				LoanDto loanDto = loanService.generateLoanResponse(200, loanObject);
				loanDto.setStatusMessage("Successful");
				logger.info(loanDto.toString());
				return ResponseEntity.ok(loanDto);
			}
			return ResponseEntity.status(204).build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(204).build();
		}

	}

	@PostMapping("/")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 204, message = "Failed") })
	public ResponseEntity<?> createLoan(@RequestBody Map<String, Object> loan) {
		try {
			boolean serviceResponse = loanService.createLoan(loan);
			logger.info("Loan is created : " + serviceResponse);
			return ResponseEntity.ok("Loan is Created");

		} catch (Exception e) {
			return ResponseEntity.status(204).build();
		}
	}

}
