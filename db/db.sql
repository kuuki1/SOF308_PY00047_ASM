CREATE DATABASE [news_application]
GO
USE [news_application]
GO
/****** Object:  Table [dbo].[comment]    Script Date: 12/2/2024 9:29:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[comment](
 [id] [bigint] IDENTITY(1,1) NOT NULL,
 [content] [nvarchar](255) NULL,
 [date] [datetime2](6) NULL,
 [post_id] [bigint] NOT NULL,
 [user_id] [bigint] NULL,
 [created_at] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
 [id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[posts]    Script Date: 12/2/2024 9:29:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[posts](
 [id] [bigint] IDENTITY(1,1) NOT NULL,
 [title] [nvarchar](255) NULL,
 [content] [text] NULL,
 [image_url] [text] NULL,
 [author_id] [bigint] NULL,
 [created_at] [datetime2](6) NULL,
 [date] [datetime2](6) NULL,
 [image] [varchar](255) NULL,
 [views] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
 [id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 12/2/2024 9:29:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
 [id] [bigint] IDENTITY(1,1) NOT NULL,
 [name] [nvarchar](255) NULL,
 [email] [varchar](255) NULL,
 [password] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
 [id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_users_email] UNIQUE NONCLUSTERED 
(
 [email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[comment] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[posts] ADD  DEFAULT (sysdatetime()) FOR [created_at]
GO
ALTER TABLE [dbo].[posts] ADD  DEFAULT ((0)) FOR [views]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FK7jok1s6lywoh0srylq8lt7tmn] FOREIGN KEY([post_id])
REFERENCES [dbo].[posts] ([id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FK7jok1s6lywoh0srylq8lt7tmn]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FKqm52p1v3o13hy268he0wcngr5] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FKqm52p1v3o13hy268he0wcngr5]
GO
ALTER TABLE [dbo].[posts]  WITH CHECK ADD  CONSTRAINT [fk_posts_author] FOREIGN KEY([author_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[posts] CHECK CONSTRAINT [fk_posts_author]
GO

