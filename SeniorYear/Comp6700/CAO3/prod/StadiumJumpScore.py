class StadiumJumpScore:
    '''
    Variables:
        statuses - list containing available options for status
        lightPenalty - 4 point deduction
        heavyPenalty - 8 point deduction
        eliminated - "E"
        retired - "R"
        withdrew - "W"
        refused - number of times refused
        
    Methods:
        __init__()
        getCompleted()
        getStatus()
        isComplete()
        setComplete()
        isEliminated()
        setEliminated()
        isWithdrawn()
        setWithdrawn()
        isRetired()
        setStart(timeIn)
        setFinish(timeIn)
        addKnockDown()
        addRefusal()
        addKnockDownRefusal()
        addDismount()
        addFall()
        getTimePenalty()
        getJumpPenalty()
        getScore()
    '''
    
    statuses = ["competitive", "withdrawn", "eliminated", "retired"]
    lightPenalty = 4
    heavyPenalty = 8
    eliminated = "E"
    retired = "R"
    withdrew = "W"
    refused = 0
    
    def __init__(self):
        self.status = "competitive" 
        self.completed = 0 #change to 1 when completed
        self.jumpPenalty = 0
        self.timePenalty = 0
        self.score = 0 #E for eliminated, R for Retired, W for withdrew, otherwise sum of penalties(time and jump)
        self.start = 0 #startTime as seconds after midnight
        self.finish = 0 #finishTime as seconds after midnight
    
    def getCompleted(self):
        return self.completed
    
    def getStatus(self):
        return self.status
    
    def isComplete(self):
        value = "True"
        if(self.completed == 0):
            value = "False"
        
        return value
    
    def setComplete(self):
        self.completed = 1
        
    def isEliminated(self):
        value = "True"
        if(self.score == "eliminated"):
            value = "False"
        
        return value
        
    def setEliminated(self):
        self.score = "eliminated"
    
    
    def isWithdrawn(self):
        value = "True"
        if(self.score == "withdrawn"):
            value = "False"
        
        return value
    
    def setWithdrawn(self):
        self.score = "withdrawn"
        
    def isRetired(self):
        value = "True"
        if(self.score == "retired"):
            value = "False"
        
        return value
    
    def setRetired(self):
        self.score = "retired"
    
    def setStart(self, timeIn):
        
        if(len(timeIn < 6)):
            raise ValueError("StadiumJumpScore.setStart: please give a six digit number representing the time as hhmmss")
        
        start = timeIn
        hours = start[:2] #gets the first two numbers
        minutes = start[2:4] #gets the third and fourth numbers
        seconds = start[4:6] #gets the fifth and sixth numbers
        
        seconds = seconds + (hours * 60 * 60) + (minutes * 60) #number of seconds past midnight
        self.start = seconds
        return self.start
        
    def setFinish(self, timeIn):
    
        if(len(timeIn < 6)):
            raise ValueError("StadiumJumpScore.setFinish: please give a six digit number representing the time as hhmmss")
        
        finish = timeIn
        hours = finish[:2] #gets the first two numbers
        minutes = finish[2:4] #gets the third and fourth numbers
        seconds = finish[4:6] #gets the fifth and sixth numbers
        
        seconds = seconds + (hours * 60 * 60) + (minutes * 60) #number of seconds past midnight
        
        if(self.start > seconds): #finish must be after start
            raise ValueError("StadiumJumpScore.setFinish: finish is before start.")
        
        self.finish = seconds
        return self.finish
        
    def addKnockDown(self):
        
        self.jumpPenalty += StadiumJumpScore.lightPenalty
        return StadiumJumpScore.lightPenalty
    
    def addRefusal(self):
        penalty = 0 #penalty to be applied
        if(StadiumJumpScore.refused >= 3):
            #kill yourself if BN, N, or TR
            self.jumpPenalty += StadiumJumpScore.heavyPenalty
            StadiumJumpScore.refused += 1
        elif(StadiumJumpScore.refused > 0):
            self.jumpPenalty += StadiumJumpScore.heavyPenalty
            penalty = StadiumJumpScore.heavyPenalty
            StadiumJumpScore.refused += 1
        else:
            self.jumpPenalty += StadiumJumpScore.lightPenalty
            penalty = StadiumJumpScore.lightPenalty
            StadiumJumpScore.refused += 1
        
        return penalty
    
    def addKnockDownRefusal(self):
        self.jumpPenalty += StadiumJumpScore.heavyPenalty + StadiumJumpScore.lightPenalty
        self.timePenalty += 6
        return StadiumJumpScore.heavyPenalty + StadiumJumpScore.lightPenalty
    
    def addDismount(self):
        self.jumpPenalty = StadiumJumpScore.eliminated
        return self.jumpPenalty
    
    def addFall(self):
        self.jumpPenalty = StadiumJumpScore.retired
        return self.jumpPenalty
    
    def getTimePenalty(self):
        if(True):#rider level incurs penalties
            return self.timePenalty
        
        else:
            return 0
    
    def getJumpPenalty(self):
        return self.jumpPenalty
    
    def getScore(self):
        if(self.isComplete()):   
            self.score = self.jumpPenalty + self.timePenalty
            return self.score
        else:
            raise ValueError("StadiumJumpScore.getScore: cannot calculate score until rider completes event")
    