INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('capybara', 'pictures/animal_1.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('cat', 'pictures/animal_2.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('sealion', 'pictures/animal_3.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('car1', 'pictures/car_1.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('car2', 'pictures/car_2.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('car3', 'pictures/car_3.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('flower1', 'pictures/flower_1.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('flower2', 'pictures/flower_2.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('flower3', 'pictures/flower_3.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('nature1', 'pictures/nature_1.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('nature2', 'pictures/nature_2.png');

INSERT INTO public.profile_pictures(
	name, path)
	VALUES ('nature3', 'pictures/nature_3.png');


INSERT INTO public.accounts(
	name, surname, profile_picture_id)
	VALUES ('Ivan', 'Novak', 2);

INSERT INTO public.accounts(
	name, surname, profile_picture_id)
	VALUES ('Olga', 'Ivanova', 6);


--password = reversi
INSERT INTO public.users(
	login, password, account_id, is_active, role_id)
	VALUES ('origami', '�-	��', '2', true, '2');

--password = reborn23
INSERT INTO public.users(
	login, password, account_id, is_active, role_id)
	VALUES ('ivan2007', 'j�����', '1', true, '2');


INSERT INTO public.credit_cards(
	name, expire_date, user_id, "number")
	VALUES ('work card', '2022-11-10T15:53:28.376Z', '1', 1453255745745715);

INSERT INTO public.credit_cards(
	name, expire_date, user_id, "number")
	VALUES ('generic card', '2022-10-12T15:53:28.376Z', '2', 5688935745834515);


INSERT INTO public.bank_accounts(
	balance, blocked, credit_card_id)
	VALUES (13, false, 2);

INSERT INTO public.bank_accounts(
	balance, blocked, credit_card_id)
	VALUES (870, false, 3);


INSERT INTO public.payments(
	user_id, destination_address, price, committed, "time", name)
	VALUES (1, 'school', 270, true, '2022-10-12T15:53:28.376Z', 'school payment');

INSERT INTO public.payments(
	user_id, destination_address, price, committed, "time", name)
	VALUES (1, 'restaurant', 270, false, null, 'restaurant');