# reviewPoint
테스트방법

========================================================================
POST http://localhost:8080/events HTTP/1.1
content-type: application/json

{
"type": "REVIEW",
"action": "ADD", /* "MOD", "DELETE" */
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "좋아요!",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
=====================================================================

POSTMAN   혹은  VisualStudioCode 의 확장프로그램 REST Client 를 통해  확인합니다
attachedPhotoIds 는  이미지가 없는 경우 null  혹은 " 이 아닌 리스트형태( []  ) 를 유지한 상태로 넘깁니다!!
=====================================================================================
이하  DDL 입니다. 

CREATE TABLE `point` (
  `userId` varchar(100) NOT NULL,
  `point` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `pointhst` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL,
  `point` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `register` varchar(100) NOT NULL,
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idx`,`userId`),
  KEY `pointHist_userId_IDX` (`userId`) USING BTREE,
  KEY `pointHist_userId_IDX2` (`userId`,`register`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `review` (
  `reviewId` varchar(100) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `placeId` varchar(100) NOT NULL,
  `content` varchar(100) DEFAULT NULL,
  `attachedPhotoId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `regDate` timestamp NULL DEFAULT NULL,
  `modDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userId`,`placeId`),
  KEY `review_reviewId_IDX` (`reviewId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
