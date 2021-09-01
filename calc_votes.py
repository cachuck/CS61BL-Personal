from math import *
all_name = []
all_role = []
all_votes = []
all_invested_money = []
all_percentages = []
all_votes_num = 0
all_invested_money_num = 0
############################
## Change to total capital #

total_club_invested = 4325

############################
############################
class Member:
    def __init__(self, name, invested_money, role):
        self.name = name
        self.invested_money = invested_money
        self.role = role
        self.equity_bonus = 0
        self.vote_bonus = 0
        if self.role == "officer":
            self.equity_bonus = 0.005
            self.vote_bonus = 1
        elif self.role == "director":
            self.equity_bonus = 0.007
            self.vote_bonus = 1
        elif self.role == "executive":
            self.equity_bonus = 0.01
            self.vote_bonus = 2
        self.votes = (ceil(self.invested_money / total_club_invested) * 
        (3 + min(8*(self.invested_money / total_club_invested)*(1 + self.equity_bonus), 4) + self.vote_bonus))
        
        all_name.append(self.name)
        all_role.append(self.role)
        all_votes.append(self.votes)
        all_invested_money.append(self.invested_money)
        global all_votes_num
        all_votes_num += self.votes
        global all_invested_money_num
        all_invested_money_num += self.invested_money

def calc_total():
    if all_invested_money_num != total_club_invested:
        print("Fund Check - Error!")
        print("Sum of Members: " + str(all_invested_money_num))
        print("Actual Capital: " + str(total_club_invested))
        return
    print("==========================================================================================================================")
    print("==========================================================================================================================")
    print("Fund Check - Success!")
    print("$" + str(all_invested_money_num) + " invested capital.")
    print(str(all_votes_num) + " total votes.")
    print("==========================================================================================================================")
    print("Roles: " + str(all_role))
    print("Names: " + str(all_name))
    print("Indiv Contribution: " + str(all_invested_money))
    print("Votes: " + str(all_votes))
    percentage_check = 0
    for nums in range(len(all_votes)):
        all_percentages.append(round(((all_votes[nums]/all_votes_num) * 100), 2))
        percentage_check += round(((all_votes[nums]/all_votes_num) * 100), 2)
    print(str(all_percentages) + " = " + str(percentage_check))
    print("==========================================================================================================================")
    for item in range(len(all_name)):
        user = item + 1
        memberRole = str(all_role[item])
        if memberRole == "executive":
            memberRole = memberRole.upper()
        else:
            memberRole = memberRole.capitalize()
        memberInfo = (str(user) + ') [' + memberRole + "] " + str(all_name[item]) + ": $" + str(all_invested_money[item]))
        idealMemberInfoLength = 45
        dashes = " "
        if len(memberInfo) < idealMemberInfoLength:
            for repeat in range(idealMemberInfoLength-len(memberInfo)):
                dashes += "-"
        print(memberInfo + dashes + " " + str(round(all_votes[item], 7)) + " votes ==> " + str(all_percentages[item]) + "%")
    print("==========================================================================================================================")
# Replace with actual members
# Format: Member("FULL NAME", AMOUNT_INVESTED, "ORG ROLE")
p1 = Member("Chris Achuck", 1000, "executive")
p2 = Member("Logan Dickey", 1000, "executive")
p3 = Member("Jason Dai", 100, "director")
p4 = Member("Test User1", 375, "director")
p5 = Member("Test User2", 250, "officer")
p6 = Member("Test User3", 1550, "officer")
p7 = Member("Test User4", 50, "member")
'''
p8 = Member("", 0, "")
p9 = Member("", 0, "")
p10 = Member("", 0, "")
p11 = Member("", 0, "")
p12 = Member("", 0, "")
p13 = Member("", 0, "")
p14 = Member("", 0, "")
p15 = Member("", 0, "")
p16 = Member("", 0, "")
p17 = Member("", 0, "")
p18 = Member("", 0, "")
p19 = Member("", 0, "")
p20 = Member("", 0, "")
'''
calc_total()