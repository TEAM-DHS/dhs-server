package com.efub.dhs.domain.program.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.efub.dhs.domain.program.entity.ProgramImage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramImageService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private String uploadImageToS3(MultipartFile imageFile) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(imageFile.getSize());
		metadata.setContentType(imageFile.getContentType());

		String filename = UUID.randomUUID().toString();
		PutObjectRequest putObjectRequest =
			new PutObjectRequest(bucket, filename, imageFile.getInputStream(), metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);

		return amazonS3.getUrl(bucket, filename).toString();
	}

	public List<ProgramImage> createProgramImages(List<MultipartFile> images) {
		return images.stream().map(image -> {
			String imageUrl;
			try {
				imageUrl = uploadImageToS3(image);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return ProgramImage.builder()
				.url(imageUrl)
				.build();
		}).collect(Collectors.toList());
	}
}
