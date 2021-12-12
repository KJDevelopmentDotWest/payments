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