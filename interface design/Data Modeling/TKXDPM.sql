CREATE TABLE Media(
	id INTEGER PRIMARY KEY NOT NULL,
	category VARCHAR(45) NOT NULL,
	price INTEGER NOT NULL,
	quantity INTEGER NOT NULL,
	title VARCHAR(45) NOT NULL,
	value INTEGER NOT NULL,
	imageUrl VARCHAR(45) NOT NULL,
	supportRushOrder BIT NOT NULL,
	weight FLOAT 
);

CREATE TABLE CD(
	id INTEGER PRIMARY KEY NOT NULL,
	artist VARCHAR(45) NOT NULL,
	recordLabel VARCHAR(45) NOT NULL,
	musicType VARCHAR(45) NOT NULL,
	releasedDate DATE,
	CONSTRAINT fk_CD_Media1
		FOREIGN KEY(id)
		REFERENCES Media(id)
);
CREATE TABLE Book(
	id INTEGER PRIMARY KEY NOT NULL,
	author VARCHAR(45) NOT NULL,
	coverType VARCHAR(45) NOT NULL,
	publisher VARCHAR(45) NOT NULL,
	publishDate DATETIME NOT NULL,
	numOfPages INTEGER NOT NULL,
	language VARCHAR(45) NOT NULL,
	bookCategory VARCHAR(45) NOT NULL,
	CONSTRAINT fk_Book_Media1
	FOREIGN KEY(id)
	REFERENCES Media(id)
);
CREATE TABLE DeliveryInfo(
	id INTEGER PRIMARY KEY NOT NULL,
	name VARCHAR(45),
	province VARCHAR(45),
	instructions VARCHAR(200),
	address VARCHAR(100)
);

CREATE TABLE DVD(
	id INTEGER PRIMARY KEY NOT NULL,
	discType VARCHAR(45) NOT NULL,
	director VARCHAR(45) NOT NULL,
	runtime INTEGER NOT NULL,
	studio VARCHAR(45) NOT NULL,
	subtitle VARCHAR(45) NOT NULL,
	releasedDate DATETIME,
	CONSTRAINT fk_DVD_Media1
	FOREIGN KEY(id)
	REFERENCES Media(id)
);
CREATE TABLE Orderr (
	id INTEGER NOT NULL,
	shippingFees VARCHAR(45),
	deliveryInfoId INTEGER NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT fk_Order_DeliveryInfo1
		FOREIGN KEY(deliveryInfoId)
		REFERENCES DeliveryInfo(id)
);

CREATE INDEX fk_Order_DeliveryInfo1_idx 
ON Orderr(deliveryInfoId);

CREATE TABLE OrderMedia(
	orderID INTEGER NOT NULL,
	price INTEGER NOT NULL,
	quantity INTEGER NOT NULL,
	mediaId INTEGER NOT NULL,
	isRushOrder BIT NOT NULL,
	CONSTRAINT fk_ordermedia_order
		FOREIGN KEY(orderID)
		REFERENCES Orderr(id),
	CONSTRAINT fk_OrderMedia_Media1
		FOREIGN KEY(mediaId)
		REFERENCES Media(id)
);
CREATE INDEX fk_ordermedia_order_idx 
ON OrderMedia(orderID);

CREATE INDEX fk_OrderMedia_Media1_idx 
ON OrderMedia(mediaId);

CREATE TABLE Invoice(
	id INTEGER PRIMARY KEY NOT NULL,
	totalAmount INTEGER NOT NULL,
	orderId INTEGER NOT NULL,
	CONSTRAINT fk_Invoice_Order1
		FOREIGN KEY(orderId)
		REFERENCES Orderr(id)
);

CREATE INDEX fk_Invoice_Order1_idx 
ON Invoice(orderId);

CREATE TABLE PaymentTransaction(
	id INTEGER NOT NULL,
	createAt DATETIME NOT NULL,
	content VARCHAR(45) NOT NULL,
	method VARCHAR(45),
	cardNumber INTEGER NOT NULL,
	invoiceId INTEGER NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT fk_PaymentTransaction_Card1
		FOREIGN KEY(invoiceId)
		REFERENCES Invoice(id)
);

CREATE INDEX fk_PaymentTransaction_Card1_idx 
ON PaymentTransaction (cardNumber);

CREATE INDEX fk_PaymentTransaction_Invoice1_idx
ON PaymentTransaction (invoiceId);
