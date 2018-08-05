class Participant:
    
    levels = ["A", "TA", "BN", "N", "TR"] #level options
    ratings = ["D1", "D2", "D3", "C1", "C2", "C3", "B", "A"] #rating options
    MAX_PINNUM = 99 #pinnie numbers should be between 1 and 99
    pinNum = 1
        
    def __init__(self, name_in, mount_in, level_in, rating_in):
        
        #make a new participant if less than 99 exist
        if(Participant.pinNum < Participant.MAX_PINNUM):
            #remove trailing/leading whitespace from parameters.
            mount_in = mount_in.strip()
            level_in = level_in.strip()
            rating_in = rating_in.strip()
            
            #check validity of parameters passed
            if(name_in.strip() == ""):
                raise ValueError("Participant.__init__: improper value for name: %s. cannot be blank!"(name_in))
            if level_in not in Participant.levels:
                raise ValueError("Participant.__init__: improper value for level: %s. expected values are A, TA, BN, N, TR, or blank"(level_in))
            if rating_in not in Participant.ratings:
                raise ValueError("Participant.__init__: improper value for rating: %s. expected values are D1, D2, D3, C1, C2, C3, B, A, or blank"(rating_in))
            
            #instantiate variables for participant instance
            self.name = name_in
            self.mount = mount_in
            self.level = level_in
            self.rating = rating_in
            
            #mount, level, and rating must be a non-empty string, if an empty string was passed in set it to "N/A"
            if(mount_in == ""):
                self.mount = "N/A"
            if(level_in == ""):
                self.level = "N/A"
            if(rating_in == ""):
                self.rating = "N/A"
            
            self.pinnie = Participant.pinNum
            #increment pinNum to denote the creation of a new participant.
            Participant.pinNum += 1
        #throw an exception if max pinNum is exceeded are taken (pinNum >= 99)
        else:
            raise ValueError("Participant.__init__: max pinnie exceeded: %d created"(Participant.pinNum))
    
    def getName(self):
        
        return self.name
    
    def setMount(self, mount_in):
        
        self.mount = mount_in
    
    def getMount(self):
        
        return self.mount
    
    def setLevel(self, level_in):
        
        self.level = level_in
    
    def getLevel(self):
        
        return self.level
    
    def setRating(self, rating_in):
        
        self.rating = rating_in
        
    def getRating(self):
        
        return self.rating
    
    def getPinnie(self):
        
        return self.pinnie
    
    
    #method to make objects sortable found at http://dev.enekoalonso.com/2008/11/28/making-your-objects-sortable/
    def __cmp__(self, other):
        return Participant.cmp(self.pinnie, other.pinnie)