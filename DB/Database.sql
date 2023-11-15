create database restaurant;
go
use restaurant;
go

-- =========================-==-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-==-=-=-=-=-=-=-==---=-------=------=-=--=-

CREATE TABLE [KhachHang] (
  [Ma_KH] int IDENTITY(2,5) not null,
  [name] nvarchar(50) not null,
  [SDT] varchar(12) not null,
  [Diem] int not null default(0),
  [email] varchar(50) default(''),
  [birthDay] date not null,
  PRIMARY KEY ([Ma_KH])
);


CREATE TABLE [Voucher] (
  [VoucherID] int IDENTITY(1,2),
  [discountPercent] float not null default(0),
  [VCode] varchar(25) not null,
  [expireDate] datetime not null,
  --[] <type>,
  --[] <type>,
  PRIMARY KEY ([VoucherID])
);

CREATE TABLE [User] (
  [user_ID] int IDENTITY(6,3),
  [username] varchar(20) not null,
  [password] varchar(50) not null,
  [role] bit not null,
  [isLocked] bit default(0) not null,
  PRIMARY KEY ([user_ID]) 
);

CREATE TABLE [Bill] (
  [bill_ID] int IDENTITY(1,1),
  [user_ID] int,
  [amount] float,
  [billDate] datetime,
  [note] varchar(150),
  [Ma_KH] int,
  [VoucherID] int,
  [order_ID] int,
  PRIMARY KEY ([bill_ID]),
  CONSTRAINT [FK_Bill.Ma_KH]
    FOREIGN KEY ([Ma_KH])
      REFERENCES [KhachHang]([Ma_KH]),
  CONSTRAINT [FK_Bill.VoucherID]
    FOREIGN KEY ([VoucherID])
      REFERENCES [Voucher]([VoucherID]),
  CONSTRAINT [FK_Bill.user_ID]
    FOREIGN KEY ([user_ID])
      REFERENCES [User]([user_ID])
);

CREATE TABLE [Food] (
  [dish_ID] int IDENTITY(1,5),
  [name] nvarchar(150),
  [price] float,
  [type] nvarchar(10),
  [isLocked] bit,
  [img] VARBINARY(max),
  PRIMARY KEY ([dish_ID])
);

CREATE TABLE [Order] (
  [order_ID] int IDENTITY(1,1),
  [bill_ID] int,
  [note] nvarchar(150),
  PRIMARY KEY ([order_ID]),
  CONSTRAINT [FK_Order.bill_ID]
    FOREIGN KEY ([bill_ID])
      REFERENCES [Bill]([bill_ID]),
  CONSTRAINT [FK_Order.order_ID_order]
    FOREIGN KEY ([bill_ID])
      REFERENCES [Bill]([bill_ID])
);

CREATE TABLE [OrderDetails] (
  [ID] int IDENTITY(1,2),
  [quantity] int,
  [order_ID] int,
  [dish_ID] int,
  [note] nvarchar(150),
  PRIMARY KEY ([ID]),
  CONSTRAINT [FK_OrderDetails.dish_ID]
    FOREIGN KEY ([dish_ID])
      REFERENCES [Food]([dish_ID]),
      CONSTRAINT [FK_OrderDetails.orderdetails]
    FOREIGN KEY ([order_ID])
      REFERENCES [Order]([order_ID])
);

CREATE TABLE [Table] (
  [table_ID] int IDENTITY(1,1),
  [order_ID] int null,
  [tableName] nvarchar(15),
  PRIMARY KEY ([table_ID]),
  CONSTRAINT [FK_Table.Order]
    FOREIGN KEY ([order_ID])
      REFERENCES [Order]([order_ID]) ON DELETE CASCADE ON UPDATE NO ACTION
);


CREATE TABLE [UserInfo] (
  [user_ID] int,
  [address] nvarchar(255),
  [name] nvarchar(50),
  [birthday] datetime,
  [SDT] varchar(12),
  [CCCD] nvarchar(12)
  CONSTRAINT [FK_Order.order_ID_user]
    FOREIGN KEY ([user_ID])
      REFERENCES [User]([user_ID]) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE [LoginHistory] (
  [user_ID] int,
  [login-time] datetime,
  [logout-time] datetime,
  CONSTRAINT [FK_LoginHistory.user_ID]
    FOREIGN KEY ([user_ID])
      REFERENCES [User]([user_ID]) ON DELETE CASCADE ON UPDATE NO ACTION
);






--=--=-=-=-=-=-=-=-=-=-=-=--=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-----=-==--=-=-=---==-=-=

go



