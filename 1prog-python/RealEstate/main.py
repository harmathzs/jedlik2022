import datetime


class Category:
	Id: int
	Name: str

class Seller:
	Id: int
	Name: str
	Phone: str

class Ad:
	Area: int
	Category: Category
	CreateAt: datetime
	Description: str
	Floors: int
	FreeOfCharge: bool
	Id: int
	ImageUrl: str
	LatLong: str
	Rooms: int
	Seller: Seller


