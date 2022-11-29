# Importing required library
import pygsheets


# Create the Client
# Enter the name of the downloaded KEYS
# file in service_account_file

class scraper(): 
	def __init__(self,sheet,tab):
		self.client = pygsheets.authorize(service_account_file="keys.json")
		self.sheet = sheet
		self.tab = tab
	def get_message_dict(self,start_range,end_range):
		spreadsht = self.client.open(self.sheet)
		worksht = spreadsht.worksheet("title", self.tab)
		output = worksht.get_values(start_range,end_range)
		message_dict = {}
		for name,chore in zip(output[0],output[1]):
			message_dict[name] = name + "'s chore for the week is " + chore
		
		return message_dict
