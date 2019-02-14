package com.voyanta.services;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voyanta.dto.ValidFileDTO;
import com.voyanta.unpackager.XMLFileUnpackager;

/**
 * @author mamadysylla
 * 
 * Rest Service to convert XML to CSV.
 *
 */
@RestController
@RequestMapping("convert")
public class CsvController {

	private static final String FILE_DIRECTORY = "/submission/";

	/**
	 * POST entry to convert a XML file to CSV.
	 * 
	 * @param xmlFile XML file to be converted
	 * @return Response Entity with CSV file.
	 */
	@PostMapping("/csv")
	public ResponseEntity<File> convertToCsv(@RequestBody String xmlFile) {
		ValidFileDTO outputFile = null;
		try {
			final File file = new File(getClass().getResource(FILE_DIRECTORY + xmlFile).toURI());

			final ValidFileDTO inputFileDTO = new ValidFileDTO(file, "inputFile");
			final XMLFileUnpackager fileUnpackager = new XMLFileUnpackager(inputFileDTO);

			outputFile = fileUnpackager.splitFile();

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(outputFile.getFile());
	}
}
