import CAO2.prod.Participant as Participant
from _overlapped import NULL


class Team:

    MAX_TEAMNUM = 20
    teamNum = 1
    teams = []
    
    def __init__(self, name_in):
         
        if(Team.teamNum < 20):
            #check validity of parameters passed
            if(name_in.strip() == ""):
                raise ValueError("Team.__init__: improper value for name: %s. cannot be blank!"(name_in))
        
            self.name = name_in
            self.team = []
            self.captainNum = 0
            self.stableManagerNum =0
            self.teamNumber = Team.teamNum
            Team.teamNum += 1
            Team.teams.append(self)
        else:
            raise ValueError("Team.__init__: the maximum number of teams has already been reached")
        
    def addParticipant(self, participant_in):
        
        if(isinstance(participant_in, Participant)):
            #if participant is already on team throw exception
            if(participant_in in self.team):
                raise ValueError("Team.addParticipant: participant is already on team %d ."(self.team.teamNumber))
            self.team.append(participant_in)
            #if first person on team default as captain and stable manager
            if(len(Team) == 1):
                Team.designateCaptain(self.team[0].getPinnie())
                Team.designateStableManager(self.team[0].getPinnie)
        #if not a valid participant throw an exception
        else:
            raise ValueError("Team.addParticipant: must pass a valid participant.")
        return participant_in.getPinnie()
            
    def designateCaptain(self, pinnie_in):
        
        oldCap = self.captainNum
        #if it's an empty team participant can't be on the team
        if(self.captainNum == 0):
            raise ValueError("Team.designateCaptain: team has no members.")
        #look through the team for the pinnie number passed in
        else:
            for teamMember in range(len(self.team)):
                if(teamMember.getPinnie() == pinnie_in):
                    self.captainNum = pinnie_in
        #if captain number didn't change then player is not on the team            
        if(self.captainNum == oldCap):
            raise ValueError("Team.designateCaptain: team does not contain participant %d."(pinnie_in)) 
        return pinnie_in
                
    def designateStableManager(self, pinnie_in):
        
        oldManager = self.stableManagerNum
        #if it's an empty team participant can't be on the team
        if(self.stableManagerNum == 0):
            raise ValueError("Team.designateStableManmager: team has no members.")
        #look through the team for the pinnie number passed in
        else:
            for teamMember in range(len(self.team)):
                if(teamMember.getPinnie() == pinnie_in):
                    self.stableManagerNum = pinnie_in
        #if manager number didn't change then player is not on the team            
        if(self.captainNum == oldManager):
            raise ValueError("Team.designateStableManager: team does not contain participant %d."(pinnie_in))  
        return pinnie_in
    
    def removeParticipant(self, pinnie_in):
       
        found = 0
        for teamMember in range(len(self.team)):
            if(teamMember.getPinnie() == pinnie_in):
                self.team.remove(teamMember)
                found = 1
        if(found == 0):
            raise ValueError("Team.removeParticipant: the team does not contain participant %d"(pinnie_in))
        return pinnie_in
    
    def getName(self):
        
        return self.name
    
    def getNumber(self):
        
        return self.teamNumber
    
    def getCaptain(self):
        
        if(self.captainNum == 0):
            return NULL
        else:
            return self.captainNum
    
    def getRoster(self):
        return sorted(self.team)
    
    def getParticipant(self, pinnie_in):
        
        found = 0
        for teamMember in range(len(self.team)):
            if(teamMember.getPinnie() == pinnie_in):
                found = 1
                return teamMember
        if(found == 0):
            raise ValueError("{Team.getParticipant: the team does not contain participant %d"(pinnie_in))
    
    
        
        
        
        
    