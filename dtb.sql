USE [master]
GO
/****** Object:  Database [ProjectS]    Script Date: 03/03/2025 8:26:35 PM ******/
CREATE DATABASE [ProjectS]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ProjectS', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.TRANHUULOC\MSSQL\DATA\ProjectS.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'ProjectS_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.TRANHUULOC\MSSQL\DATA\ProjectS_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [ProjectS] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ProjectS].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ProjectS] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ProjectS] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ProjectS] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ProjectS] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ProjectS] SET ARITHABORT OFF 
GO
ALTER DATABASE [ProjectS] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [ProjectS] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ProjectS] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ProjectS] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ProjectS] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ProjectS] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ProjectS] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ProjectS] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ProjectS] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ProjectS] SET  ENABLE_BROKER 
GO
ALTER DATABASE [ProjectS] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ProjectS] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ProjectS] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ProjectS] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ProjectS] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ProjectS] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ProjectS] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ProjectS] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ProjectS] SET  MULTI_USER 
GO
ALTER DATABASE [ProjectS] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ProjectS] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ProjectS] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ProjectS] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ProjectS] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [ProjectS] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [ProjectS] SET QUERY_STORE = ON
GO
ALTER DATABASE [ProjectS] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [ProjectS]
GO
/****** Object:  Table [dbo].[cart_items]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart_items](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[product_variant_id] [int] NULL,
	[quantity] [int] NOT NULL,
	[total] [float] NOT NULL,
	[cart_id] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[carts]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[carts](
	[username] [varchar](50) NOT NULL,
	[quantity] [int] NOT NULL,
	[total] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[categories]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[categories](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_items]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_items](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[product_variant_value_id] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[total] [float] NOT NULL,
	[price_at_order] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[order_date] [datetime] NOT NULL,
	[address] [nvarchar](255) NOT NULL,
	[total_amount] [float] NOT NULL,
	[status] [nvarchar](50) NOT NULL,
	[notes] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_variant_values]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_variant_values](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NOT NULL,
	[price] [float] NOT NULL,
	[old_price] [float] NOT NULL,
	[stock] [int] NOT NULL,
	[turnBuy] [int] NOT NULL,
	[image] [nvarchar](100) NOT NULL,
	[sku] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[products]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[name] [nvarchar](100) NOT NULL,
	[image] [nvarchar](100) NOT NULL,
	[description] [nvarchar](max) NULL,
	[turnBuy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[username] [varchar](50) NOT NULL,
	[fullname] [nvarchar](255) NOT NULL,
	[birthday] [date] NULL,
	[password] [nvarchar](255) NOT NULL,
	[email] [nvarchar](255) NULL,
	[phone] [nvarchar](15) NULL,
	[create_at] [datetime] NULL,
	[update_at] [datetime] NOT NULL,
	[address] [nvarchar](255) NULL,
	[role] [varchar](50) NOT NULL,
	[enabled] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[variant_values]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[variant_values](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[variant_id] [int] NOT NULL,
	[value] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[variants]    Script Date: 03/03/2025 8:26:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[variants](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [create_at]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [update_at]
GO
ALTER TABLE [dbo].[cart_items]  WITH CHECK ADD FOREIGN KEY([cart_id])
REFERENCES [dbo].[carts] ([username])
GO
ALTER TABLE [dbo].[cart_items]  WITH CHECK ADD FOREIGN KEY([product_variant_id])
REFERENCES [dbo].[product_variant_values] ([id])
GO
ALTER TABLE [dbo].[carts]  WITH CHECK ADD FOREIGN KEY([username])
REFERENCES [dbo].[users] ([username])
GO
ALTER TABLE [dbo].[order_items]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_items]  WITH CHECK ADD FOREIGN KEY([product_variant_value_id])
REFERENCES [dbo].[product_variant_values] ([id])
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD FOREIGN KEY([username])
REFERENCES [dbo].[users] ([username])
GO
ALTER TABLE [dbo].[product_variant_values]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [dbo].[categories] ([id])
GO
ALTER TABLE [dbo].[variant_values]  WITH CHECK ADD FOREIGN KEY([variant_id])
REFERENCES [dbo].[variants] ([id])
GO
USE [master]
GO
ALTER DATABASE [ProjectS] SET  READ_WRITE 
GO
