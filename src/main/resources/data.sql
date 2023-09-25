INSERT INTO users (id, email, password, first_name, last_name, avatar_image_url) VALUES 
( DEFAULT, 'testuser@gmail.com', 'password', 'Test', 'User', 'https://www.nicepng.com/png/full/128-1280406_view-user-icon-png-user-circle-icon-png.png'), 
( DEFAULT, 'q@q', '111111', 'John', 'Doe', 'https://www.nicepng.com/png/full/128-1280406_view-user-icon-png-user-circle-icon-png.png'),
( DEFAULT, 'johndoe@outlook.com', 'qwerty', 'John', 'Doe', 'https://th.bing.com/th/id/OIP.ExWpNcwPF2JAaeUcm-nypwHaD4?pid=ImgDet&rs=1'),
( DEFAULT, 'linkinpark@gmail.com', 'somepassword', 'Linkin', 'Park', 'https://static.billboard.com/files/media/linkin-park-chester-bennington-live-june-2017-a-02-billboard-1548-compressed.jpg'),
( DEFAULT, 'eminem@outlook.com', 'eminem', 'Marshall', 'Mathers', 'https://www.biography.com/.image/t_share/MTQ3NjM5MTEzMTc5MjEwODI2/eminem_photo_by_dave_j_hogan_getty_images_entertainment_getty_187596325.jpg'),
( DEFAULT, 'adele@outlook.com', 'songwriter', 'Adele', 'Adkins', 'https://media13.s-nbcnews.com/i/mpx/2704722219/2022_11/1667393216518_tdy_pop_8a_adele_221102_1920x1080-4bgpx1.jpg'),
( DEFAULT, 'lawrence@outlook.com', 'actress', 'Jennifer', 'Lawrence', 'https://cdn.unitycms.io/images/64k8YY5F4EWAjUSbvhdkYB.jpg?op=ocroped&val=1200,1200,861,360,24,72&sum=POZNeHD-dY4'),
( DEFAULT, 'aniston@outlook.com', 'actress', 'Jennifer', 'Aniston', 'https://media-cldnry.s-nbcnews.com/image/upload/t_fit-760w,f_auto,q_auto:best/newscms/2020_07/3227271/200212-jennifer-aniston-cs-900a.jpg'),
( DEFAULT, 'elon.musk@outlook.com', 'tesla1', 'Elon', 'Musk', 'https://thenextavenue.com/wp-content/uploads/2021/01/Elon-Musk-five-cinco.jpg');



INSERT INTO profiles (id, owner_id, background_image_url) VALUES
(DEFAULT, 1, 'https://fthmb.tqn.com/vMHG2Hi44XBqddh93WTo3nkWESU=/5000x3000/filters:fill(auto,1)/low-poly-background-672623312-5a5a8563e258f800370a105a.jpg'),
(DEFAULT, 2, 'https://fthmb.tqn.com/vMHG2Hi44XBqddh93WTo3nkWESU=/5000x3000/filters:fill(auto,1)/low-poly-background-672623312-5a5a8563e258f800370a105a.jpg');

INSERT INTO profiles(id, owner_id, background_image_url, current_city, current_country, born_city, born_country, dob, gender, marital_status, school_name, job_title, company_name, company_url) VALUES
(DEFAULT, 3, 'https://th.bing.com/th/id/R.1ccd272fae1aad63e81b10d0e0fd81d9?rik=SpRvNQ33fNF0Ig&pid=ImgRaw&r=0', 'Phoenix', 'USA', 'Portland', 'USA', null, 'Male', 'Single', 'University of Idaho', 'Graphic Designer', 'IdeaDesign', null ),
(DEFAULT, 4, 'https://talenthouse-res.cloudinary.com/image/upload/c_limit,f_auto,h_630,q_65,w_1200/v1396974874/invites-97/nozfham1an2fboppxesk.jpg', 'Agoura Hills', 'USA', null , null, null, 'Male', 'Single', null, 'Singers', 'Linkin Park', 'https://www.linkinpark.com/'),
(DEFAULT, 5, 'https://www.eminem.net/wp-content/uploads/eminem-logo-featured.png', 'Los Angeles', 'USA', 'Detroit', 'USA', null, 'Male', 'Married', null, 'Singer', null, null ),
(DEFAULT, 6, 'https://a-static.besthdwallpaper.com/adele-music-star-with-blonde-long-hair-and-blue-background-wallpaper-2560x800-90702_59.jpg', 'London', 'UK', 'London', 'UK', null, 'Female', 'Married', 'BRIT School for Performing Arts and Technology', 'Singer', null, null ),
(DEFAULT, 7, 'https://i.pinimg.com/originals/31/65/86/3165864430cbf7b2552e91e06a64d0b9.jpg', null, 'USA', 'Indian Hills', 'USA', null, 'Female', 'Single', null, 'Actress', 'Hollywood', null ),
(DEFAULT, 8, 'https://history-biography.com/wp-content/uploads/2021/09/aniston.jpg', null, 'USA', 'Sherman-Ox', 'USA', null, 'Female', 'Divorced', null, 'Actress', 'Hollywood', null ),
(DEFAULT, 9, 'https://wallpapercave.com/wp/wp5291025.jpg', null, 'USA', 'Pretoria', 'South Africa', null, 'Female', 'Divorced', 'University of Pennsylvania', 'CEO', 'Tesla', null );

INSERT INTO posts (id, text, image_url, vote_count, author_id) VALUES
( DEFAULT, '', 'https://www.brainyquote.com/photos_tr/en/g/georgedennisonprentice/160105/georgedennisonprentice1-2x.jpg', 0, 2),
( DEFAULT, 'The classic', 'https://i.imgur.com/fhgzVEt.jpeg', 0, 1),
( DEFAULT, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', '', 0, 1),
( DEFAULT, '', 'https://ohtopten.com/wp-content/uploads/2015/04/Love-Quotes-22.jpg', 0, 8),
( DEFAULT, 'A friend knows the song in my heart and sings it to me when my memory fails.', 'https://www.alt1051.com/wp-content/uploads/2021/01/GettyImages-56793352-scaled.jpg', 0, 4),
( DEFAULT, '“I`m selfish, impatient and a little insecure. I make mistakes, I am out of control and at times hard to handle. But if you can`t handle me at my worst, then you sure as hell don`t deserve me at my best.”― Marilyn Monroe', '', 0, 8),
( DEFAULT, '“Don’t let yesterday take up too much of today.” — Will Rogers', 'https://9to5mac.com/wp-content/uploads/sites/6/2022/11/jennifer-lawrence-hunger-games-2048x1152-1.jpg?quality=82&strip=all', 0, 7),
( DEFAULT, '', 'https://wallpapercave.com/wp/wp3324779.jpg', 0, 5),
( DEFAULT, 'The classic', 'https://eskipaper.com/images/eminem-1.jpg',0 , 5);
